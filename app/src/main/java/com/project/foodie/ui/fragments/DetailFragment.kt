package com.project.foodie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.foodie.databinding.FragmentDetailBinding
import com.project.foodie.ui.viewmodels.DetailFragmentViewModel
import com.project.foodie.ui.viewmodels.HomeFragmentViewModel
import com.project.foodie.utils.getDescription
import com.project.foodie.utils.getImage
import com.project.foodie.utils.getLocation
import com.project.foodie.utils.getStar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var amount: Int = 1
    private var singlePrice = 0
    private lateinit var viewModel: DetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()
        val yemekValue = bundle.yemekArg
        initFragment(yemekValue.yemekPrice)

        with(binding) {
            detailLocationTextView.text = yemekValue.yemekName.getLocation()
            detailStarTextView.text = yemekValue.yemekName.getStar()
            Glide.with(requireContext()).load(yemekValue.yemekPict.getImage())
                .into(detailImageView);
            detailNameTextView.text = yemekValue.yemekName
            detailPriceTextView.text = buildString {
                append(yemekValue.yemekPrice)
                append(" ₺")
            }
            detailDescriptionTextView.text = yemekValue.yemekName.getDescription()

            decreaseButton.setOnClickListener {
                amount--
                if (amount < 1) amount = 1
                binding.amountTextView.text = amount.toString()
                binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)
            }
            detailBackButton.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }

            increaseButton.setOnClickListener {
                amount++
                if (amount > 10) amount = 10
                binding.amountTextView.text = amount.toString()
                binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)

            }

            addCartButton.setOnClickListener {
                viewModel.insertCart(
                    yemekName = yemekValue.yemekName,
                    yemekPict = yemekValue.yemekPict,
                    yemekPrice = yemekValue.yemekPrice,
                    yemekOrderAmount = amount,
                    userName = "Resul"
                )
            }
        }


        val view = binding.root
        return view
    }

    private fun calculateTotalPrice(singleItemPrice: Int, amount: Int): String {
        return buildString {
            append(singleItemPrice * amount)
            append(" ₺")
        }
    }

    private fun initFragment(price: Int): Unit {
        singlePrice = price
        binding.amountTextView.text = "1"
        binding.totalPrice.text = calculateTotalPrice(singlePrice, amount)

    }

}