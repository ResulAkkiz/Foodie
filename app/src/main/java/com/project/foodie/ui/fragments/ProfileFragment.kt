package com.project.foodie.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.MainActivity
import com.project.foodie.R
import com.project.foodie.data.entity.User
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentProfileBinding
import com.project.foodie.databinding.FragmentSignupBinding
import com.project.foodie.firebase.FirebaseFirestoreResult
import com.project.foodie.ui.viewmodels.ProfileFragmentViewModel
import com.project.foodie.ui.viewmodels.SignupFragmentViewModel
import com.project.foodie.utils.getImage
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
        binding.scrollView2.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE

        binding.profileUpdateButton.setOnClickListener {
            Log.e("TAG", "set onclick listener")
            checkValidation(R.drawable.outline_info_24, "Lütfen gerekli yerleri doldurunuz.") {
                if (user != null) {
                    Log.e("TAG", "updateuser before")
                    viewModel.updateUser(
                        user!!.userId,
                        hashMapOf(
                            "userName" to binding.profileNameEditText.text.toString().trim(),
                            "userSurname" to binding.profileSurnameEditText.text.toString().trim(),
                            "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                .trim(),
                            "userAddress" to binding.profileAdressEditText.text.toString().trim(),
                        ),
                    ) { result ->
                        when (result) {
                            is FirebaseFirestoreResult.Success<*> -> {
                                showBottomSheetDialog(
                                    R.drawable.success_gif_im,
                                    "Güncelleme işlemi başarıyla gerçekleşti."
                                )
                            }

                            is FirebaseFirestoreResult.Failure -> {
                                showBottomSheetDialog(
                                    R.drawable.failure_gif_im,
                                    "Bir hata meydana geldi: ${result.error}"
                                )
                            }

                        }
                        Log.e("TAG", result.toString())

                    }

                }
            }
        }

        binding.signOutButton.setOnClickListener {
            viewModel.signOut { result ->
                if (result) {
                    startActivity(Intent(requireContext(),MainActivity::class.java))
                    requireActivity().finish()
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
            binding.scrollView2.visibility = View.VISIBLE
            binding.progressBar3.visibility = View.GONE


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

        Glide.with(requireContext()).load(resInt)
            .into(bottomSheetBinding.errorImage);
        bottomSheetBinding.errorDescription.text = info
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }


}