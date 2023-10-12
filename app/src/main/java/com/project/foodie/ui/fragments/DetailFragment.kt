package com.project.foodie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.project.foodie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var amount: Int = 1
    private var singlePrice = 0.00
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initFragment()
        binding.decreaseButton.setOnClickListener {
            amount--
            if (amount < 1) amount = 1
            binding.amountTextView.text = amount.toString()
            binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)
        }
        binding.detailBackButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.increaseButton.setOnClickListener {
            amount++
            if (amount > 10) amount = 10
            binding.amountTextView.text = amount.toString()
            binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)

        }

        binding.addCartButton.setOnClickListener {
            //Todo: Add to chart process
        }


        val view = binding.root
        return view
    }

    private fun calculateTotalPrice(singleItemPrice: Double, amount: Int): String {
        return buildString {
            append(singleItemPrice * amount)
            append(" ₺")
        }
    }

    private fun initFragment(): Unit {
        singlePrice = 20.00 //price value will come from args
        binding.amountTextView.text = "1"
        binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)

    }

}