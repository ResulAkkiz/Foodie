package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.project.foodie.R
import com.project.foodie.databinding.FragmentMainBinding
import com.project.foodie.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val smoothbar = binding.bottomBar
        val popupMenu = PopupMenu(requireContext(), smoothbar)
        val menuResId = R.menu.bottom_nav_menu
        popupMenu.menuInflater.inflate(menuResId, popupMenu.menu)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainFragmentView) as NavHostFragment
        smoothbar.setupWithNavController(popupMenu.menu, navHostFragment.navController)
        return view
    }

}