package com.project.foodie.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.project.foodie.R
import com.project.foodie.databinding.FragmentProfileBinding
import com.project.foodie.databinding.FragmentSignupBinding
import com.project.foodie.databinding.FragmentSplashBinding
import com.project.foodie.ui.viewmodels.LoginFragmentViewModel
import com.project.foodie.ui.viewmodels.SplashFragmentViewModel
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
        viewModel.currentUser()

        viewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->

            Handler(Looper.getMainLooper()).postDelayed({
                if (firebaseUser != null) {
                    Navigation.findNavController(requireView())
                        .navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                } else {
                    Navigation.findNavController(requireView())
                        .navigate(SplashFragmentDirections.actionSplashFragmentToBoardingFragment())
                }

            }, 1000)
        }

//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.action_splashFragment_to_boardingFragment)
//        }, 250)
        return view
    }
}