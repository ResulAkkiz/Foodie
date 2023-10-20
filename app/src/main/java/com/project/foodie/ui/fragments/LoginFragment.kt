package com.project.foodie.ui.fragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.foodie.HomeActivity
import com.project.foodie.R
import com.project.foodie.databinding.FragmentBoardingBinding
import com.project.foodie.databinding.FragmentErrorBottomSheetBinding
import com.project.foodie.databinding.FragmentLoginBinding
import com.project.foodie.ui.viewmodels.HomeFragmentViewModel
import com.project.foodie.ui.viewmodels.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: LoginFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.loginButton.setOnClickListener {
            checkValidation(R.drawable.outline_info_24, "Lütfen gerekli yerleri doldurunuz.") {
                viewModel.signInWithEmailAndPassword(
                    binding.loginEmailEditText.text.toString().trim(),
                    binding.loginPasswordEditText.text.toString().trim()
                )
            }

        }
        binding.signupButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signupFragment)
        }
        viewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        viewModel.exception.observe(viewLifecycleOwner) { error ->
            showBottomSheetDialog(R.drawable.outline_info_24, error.toString())

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.loginEmailEditText.text.isNullOrBlank() || binding.loginPasswordEditText.text.isNullOrBlank()) {
            showBottomSheetDialog(resInt, info)
            with(binding) {
                if (loginEmailEditText.text.isNullOrBlank()) loginEmailEditText.error =
                    "E-mail boş bırakılamaz."
                if (loginPasswordEditText.text.isNullOrBlank()) loginPasswordEditText.error =
                    "Şifre boş bırakılamaz."
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