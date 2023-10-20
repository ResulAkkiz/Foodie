package com.project.foodie.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.project.foodie.HomeActivity
import com.project.foodie.R
import com.project.foodie.databinding.FragmentSplashBinding
import com.project.foodie.ui.viewmodels.SplashFragmentViewModel
import com.project.foodie.utils.LocalDataManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SplashFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SplashFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        Glide.with(this).load(R.drawable.splash_icon_ic)
            .into(binding.splashImageView)
        viewModel.currentUser()

        viewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->

            Handler(Looper.getMainLooper()).postDelayed({
                if (firebaseUser != null) {
                    startActivity(Intent(requireContext(),HomeActivity::class.java))
                    requireActivity().finish()
                } else {
                    val result =
                        LocalDataManager.getSharedPreference(requireContext(), "goBoarding", true)
                    if (result) {
                        Navigation.findNavController(requireView())
                            .navigate(SplashFragmentDirections.actionSplashFragmentToBoardingFragment())
                    } else {
                        Navigation.findNavController(requireView())
                            .navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                    }

                }

            }, 4000)
        }


        return view
    }
}