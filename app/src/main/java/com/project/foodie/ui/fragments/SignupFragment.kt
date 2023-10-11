package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.R
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentLoginBinding
import com.project.foodie.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.signupButton.setOnClickListener {
            checkValidation(R.drawable.outline_info_24, "Lütfen gerekli alanları doldurunuz") {
                println("Signup Process")
            }
        }
        binding.loginButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        return view
    }

    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.signupEmailEditText.text.isNullOrBlank() || binding.signupPasswordEditText.text.isNullOrBlank() || binding.signupPasswordConfirmEditText.text.isNullOrBlank()) {

            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )
            with(binding){
                if ( signupEmailEditText.text.isNullOrBlank()) signupEmailEditText.error = "E-mail boş bırakılamaz."
                if ( signupPasswordEditText.text.isNullOrBlank()) signupPasswordEditText.error = "Şifre boş bırakılamaz."
                if ( signupPasswordConfirmEditText.text.isNullOrBlank()) signupPasswordConfirmEditText.error = "Şifre Onaylama boş bırakılamaz."
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