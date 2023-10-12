package com.project.foodie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.foodie.data.entity.Sepet
import com.project.foodie.databinding.FragmentCartBinding
import com.project.foodie.ui.adapters.CartRecyclerViewAdapter


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.cartRecyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val sepetListesi = arrayListOf(
            Sepet(1, "Pizza", "resim1.jpg", 30, 1, "Kullanıcı1"),
            Sepet(2, "Hamburger", "resim2.jpg", 25, 3, "Kullanıcı2"),
            Sepet(3, "Makarna", "resim3.jpg", 40, 4, "Kullanıcı3")
        )
        var adapter=CartRecyclerViewAdapter(sepetListesi,requireContext()){totalPrice ->
            binding.totalPriceFabButton.text=buildString {
                append(totalPrice)
                append(" ₺")
            }

        }
        binding.cartRecyclerView.adapter=adapter
        Log.e("TAG","total price :${adapter.totalPrice}")

        return view
    }


}