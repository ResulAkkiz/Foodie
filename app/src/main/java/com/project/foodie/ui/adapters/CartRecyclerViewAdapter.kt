package com.project.foodie.ui.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.project.foodie.R
import com.project.foodie.data.entity.Sepet
import com.project.foodie.databinding.CartSingleItemBinding
import com.project.foodie.databinding.DeleteConfirmDialogBinding
import com.project.foodie.ui.viewmodels.CartFragmentViewModel
import com.project.foodie.utils.getImage

class CartRecyclerViewAdapter(
    var cartList: List<Sepet>,
    var mContext: Context,
    var viewModel: CartFragmentViewModel,
    var totalPriceListener: (totalPrice: Int) -> Unit,
) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    var totalPrice = 0;

    inner class CartViewHolder(var view: CartSingleItemBinding) : ViewHolder(view.root) {
        fun bind(cartItem: Sepet) {
            with(view) {
                orderImageView.setImageResource(R.drawable.pizza_sample_im)
                var singleOrderTotalPrice =
                    cartItem.sepetYemekPrice * cartItem.sepetYemekOrderAmount
                totalPrice += singleOrderTotalPrice
                var amount = cartItem.sepetYemekOrderAmount
                orderAmountTextView.text = amount.toString()
                orderNameTextView.text = cartItem.sepetYemekName
                orderTotalPrice.text = buildString {
                    append(singleOrderTotalPrice)
                    append(" ₺")
                }
                Glide.with(mContext).load(cartItem.sepetYemekPict.getImage()).into(orderImageView);
                deleteButton.setOnClickListener {
                    showDeleteConfirmDialogBox(positiveButton = {
                        viewModel.deleteCartItem(cartItem.sepetYemekId)
                        updateTotalPrice()
                    }, {})

                }
                totalPriceListener(totalPrice)

                decreaseButton.setOnClickListener {

                    amount--
                    if (amount == 0) {
                        //Todo: Delete process here

                    }
                    orderAmountTextView.text = amount.toString()
                    singleOrderTotalPrice = cartItem.sepetYemekPrice * amount
                    orderTotalPrice.text = buildString {
                        append(singleOrderTotalPrice)
                        append(" ₺")
                    }
                    cartItem.sepetYemekOrderAmount = amount
                    updateTotalPrice()


                }
                increaseButton.setOnClickListener {

                    amount++
                    if (amount > 10) amount = 10
                    orderAmountTextView.text = amount.toString()

                    singleOrderTotalPrice = cartItem.sepetYemekPrice * amount
                    orderTotalPrice.text = buildString {
                        append(singleOrderTotalPrice)
                        append(" ₺")
                    }
                    cartItem.sepetYemekOrderAmount = amount

                    updateTotalPrice()


                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = CartSingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentCartItem = cartList[position]
        println(currentCartItem.sepetYemekOrderAmount)
        holder.bind(currentCartItem)
    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        for (item in cartList) {
            totalPrice += item.sepetYemekPrice * item.sepetYemekOrderAmount
        }
        Log.e("TAG","Total price in adapter : $totalPrice")
        totalPriceListener(totalPrice)
    }

    private fun showDeleteConfirmDialogBox(positiveButton: () -> Unit, negativeButton: () -> Unit) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view =
            DeleteConfirmDialogBinding.inflate(LayoutInflater.from(mContext), null, false)
        view.infoDescription.text = "Sectiğiniz ürünü sepetten kaldırmak istediğinize emin misiniz?"
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.positiveButton.setOnClickListener {
            positiveButton()
            dialog.dismiss()
        }
        view.negativeButton.setOnClickListener {
            negativeButton()
            dialog.dismiss()
        }

        dialog.show()
    }

}