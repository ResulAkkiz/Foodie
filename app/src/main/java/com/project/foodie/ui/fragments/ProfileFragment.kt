package com.project.foodie.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.R
import com.project.foodie.data.entity.User
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentProfileBinding
import com.project.foodie.databinding.FragmentSignupBinding
import com.project.foodie.firebase.FirebaseFirestoreResult
import com.project.foodie.ui.viewmodels.ProfileFragmentViewModel
import com.project.foodie.ui.viewmodels.SignupFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var user: User? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.profileUpdateButton.setOnClickListener {
            Log.e("TAG", "set onclick listener")
            checkValidation(R.drawable.outline_info_24, "Lütfen gerekli yerleri doldurunuz.") {
                if (user != null) {
                    viewModel.updateUser(
                        user!!.userId,
                        hashMapOf(
                            "userName" to binding.profileNameEditText.text.toString().trim(),
                            "userSurname" to binding.profileSurnameEditText.text.toString().trim(),
                            "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                .trim(),
                            "userAddress" to binding.profileAdressEditText.text.toString().trim(),
                        ),
                    )
                }
            }

            viewModel.updateResult.observe(viewLifecycleOwner) { result ->
                Log.e("TAG", result.toString())
                when (result) {
                    is FirebaseFirestoreResult.Success<*> -> {
                        showBottomSheetDialog(
                            R.drawable.baseline_check_circle_outline_24,
                            "Güncelleme işlemi başarıyla gerçekleşti."
                        )
                    }

                    is FirebaseFirestoreResult.Failure -> {
                        showBottomSheetDialog(
                            R.drawable.outline_info_24,
                            "Bir hata meydana geldi: ${result.error}"
                        )
                    }
                }
            }
        }



        viewModel.getUserResult.observe(viewLifecycleOwner) { result ->
            Log.e("TAG", "$result getUserResult")
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    if (result.data is User)
                        user = result.data
                    with(binding) {
                        profileAdressEditText.setText(user?.userAddress)
                        profileNameEditText.setText(user?.userName)
                        profileSurnameEditText.setText(user?.userSurname)
                        profilePhoneNumberEditText.setText(user?.userPhoneNumber)
                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    showBottomSheetDialog(
                        R.drawable.outline_info_24,
                        "Bir hata meydana geldi: ${result.error}"
                    )
                }

            }


        }


        return binding.root
    }


    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.profileAdressEditText.text.isNullOrBlank() || binding.profileNameEditText.text.isNullOrBlank() || binding.profileSurnameEditText.text.isNullOrBlank() || binding.profilePhoneNumberEditText.text.isNullOrBlank()) {

            showBottomSheetDialog(resInt, info)

            with(binding) {
                if (profileSurnameEditText.text.isNullOrBlank()) profileSurnameEditText.error =
                    "Soyadınızı lütfen boş bırakmayınız."
                if (profileNameEditText.text.isNullOrBlank()) profileNameEditText.error =
                    "Adınızı lütfen boş bırakmayınız."
                if (profileAdressEditText.text.isNullOrBlank()) profileAdressEditText.error =
                    "Adres bilginizi lütfen boş bırakmayınız."
                if (profilePhoneNumberEditText.text.isNullOrBlank()) profilePhoneNumberEditText.error =
                    "Telefon numarınızı lütfen boş bırakmayınız."
            }
        } else {
            onSuccessful()

        }

    }

    private fun showBottomSheetDialog(resInt: Int, info: String) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )

        bottomSheetBinding.errorImage.setImageResource(resInt)
        bottomSheetBinding.errorDescription.text = info
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }


}