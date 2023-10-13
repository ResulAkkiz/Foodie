package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.R
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentProfileBinding
import com.project.foodie.databinding.FragmentSignupBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.profileUpdateButton.setOnClickListener {
            checkValidation(R.drawable.outline_info_24,"Lütfen gerekli yerleri doldurunuz."){
                //Todo : update process
                println("Update Process")
            }
        }
        return view
    }

    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.profileAdressEditText.text.isNullOrBlank() || binding.profileNameEditText.text.isNullOrBlank() || binding.profileSurnameEditText.text.isNullOrBlank() || binding.profilePhoneNumberEditText.text.isNullOrBlank()) {

            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )
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
            bottomSheetBinding.errorImage.setImageResource(resInt)
            bottomSheetBinding.errorDescription.text = info
            dialog.setCancelable(true)
            dialog.setContentView(bottomSheetBinding.root)
            dialog.show()
        } else {
            onSuccessful()

        }

    }


}