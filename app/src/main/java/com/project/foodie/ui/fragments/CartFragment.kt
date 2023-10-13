package com.project.foodie.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
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


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val sepetListesi = arrayListOf(
            Sepet(1, "Pizza", "resim1.jpg", 30, 1, "Kullanıcı1"),
            Sepet(2, "Hamburger", "resim2.jpg", 25, 3, "Kullanıcı2"),

        )
        var adapter = CartRecyclerViewAdapter(sepetListesi, requireContext()) { totalPrice ->
            binding.totalPriceFabButton.text = buildString {
                append(totalPrice)
                append(" ₺")
            }

        }

        binding.totalPriceFabButton.setOnClickListener {
            showConfirmDialogBox()
        }

        binding.cartRecyclerView.adapter = adapter


        return view
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
            CartSuccessfulDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        Glide.with(this).load(R.drawable.order_successful_im).into(view.orderSuccessfulImageView)
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }


}