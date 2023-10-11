package com.project.foodie.ui.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.R
import com.project.foodie.databinding.FragmentBoardingBinding
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.loginButton.setOnClickListener {
            checkValidation(R.drawable.outline_info_24,"Lütfen gerekli yerleri doldurunuz."){
                println("Login process")
            }
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_mainFragment)
        }
        binding.signupButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkValidation(resInt: Int, info:String, onSuccessful:()->Unit) {
        if (binding.loginEmailEditText.text.isNullOrBlank() || binding.loginPasswordEditText.text.isNullOrBlank()) {

            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )
            with(binding){
                if ( loginEmailEditText.text.isNullOrBlank()) loginEmailEditText.error = "E-mail boş bırakılamaz."
                if ( loginPasswordEditText.text.isNullOrBlank()) loginPasswordEditText.error = "Şifre boş bırakılamaz."
            }
            bottomSheetBinding.errorImage.setImageResource(resInt)
            bottomSheetBinding.errorDescription.text=info
            dialog.setCancelable(true)
            dialog.setContentView(bottomSheetBinding.root)
            dialog.show()
        } else {
            onSuccessful()

        }

    }


}