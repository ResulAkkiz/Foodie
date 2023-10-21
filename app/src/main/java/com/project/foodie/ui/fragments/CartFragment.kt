package com.project.foodie.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.project.foodie.R
import com.project.foodie.data.entity.Sepet
import com.project.foodie.databinding.CartAlertDialogBinding
import com.project.foodie.databinding.CartSuccessfulDialogBinding
import com.project.foodie.databinding.FragmentCartBinding
import com.project.foodie.ui.adapters.CartRecyclerViewAdapter
import com.project.foodie.ui.viewmodels.CartFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CartFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.cartList.observe(viewLifecycleOwner) { cartList ->
           Log.e("TAG","cartList : ${cartList.size}")
            var adapter = CartRecyclerViewAdapter(cartList, requireContext(),viewModel)
            binding.cartRecyclerView.adapter = adapter
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){totalPrice->
            if (totalPrice == 0) {
                binding.totalPriceFabButton.visibility = View.GONE
            } else {
                binding.totalPriceFabButton.visibility = View.VISIBLE
                binding.totalPriceFabButton.text = buildString {
                    append(totalPrice)
                    append(" ₺")
                }
            }
        }

        binding.totalPriceFabButton.setOnClickListener {
            showConfirmDialogBox()
        }

        return view

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartList()
    }

    private fun showConfirmDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view =
            CartAlertDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.positiveButton.setOnClickListener {
            dialog.dismiss()
            showSuccessfulDialogBox()

        }
        view.negativeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSuccessfulDialogBox() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view =
            CartSuccessfulDialogBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )
        Glide.with(this).load(R.drawable.order_successful_im)
            .into(view.orderSuccessfulImageView)
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }


}