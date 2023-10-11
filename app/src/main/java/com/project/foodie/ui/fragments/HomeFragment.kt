package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.project.foodie.R
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.FragmentHomeBinding
import com.project.foodie.databinding.FragmentLoginBinding
import com.project.foodie.ui.adapters.YemekRecyclerViewAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.yemeklerRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        val yemekListesi = listOf(
            Yemek(1, "Pizza", "pizza.jpg", 15.99),
            Yemek(2, "Hamburger", "hamburger.jpg", 8.99),
            Yemek(3, "Pasta", "pasta.jpg", 12.49),
            Yemek(4, "Çorba", "corba.jpg", 5.99),
            Yemek(5, "Salata", "salata.jpg", 7.49)
        )
        binding.yemeklerRecyclerView.adapter =
            YemekRecyclerViewAdapter(yemekListesi, requireContext())

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}