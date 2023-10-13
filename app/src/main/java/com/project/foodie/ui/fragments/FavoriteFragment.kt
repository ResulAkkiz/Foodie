package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.foodie.R
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.FragmentCartBinding
import com.project.foodie.databinding.FragmentFavoriteBinding
import com.project.foodie.databinding.FragmentHomeBinding
import com.project.foodie.ui.adapters.FavoriteRecyclerViewAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.favoriteRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        val yemekListesi = arrayListOf(
            Yemek(1, "Pizza", "pizza.jpg", 15),
            Yemek(2, "Hamburger", "hamburger.jpg", 24),

        )
        binding.favoriteRecyclerView.adapter=
            FavoriteRecyclerViewAdapter(yemekListesi,requireContext())
        return view
    }

}