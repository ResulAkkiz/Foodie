package com.project.foodie.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.foodie.R
import com.project.foodie.data.entity.Sepet
import com.project.foodie.databinding.CartSingleItemBinding

class CartRecyclerViewAdapter(var cartList: ArrayList<Sepet>, var mContext: Context,var totalPriceListener:(totalPrice:Int)->Unit) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    var totalPrice=0;

    inner class CartViewHolder(var view: CartSingleItemBinding) : ViewHolder(view.root) {
        fun bind(cartItem: Sepet) {
            with(view) {

                orderImageView.setImageResource(R.drawable.pizza_sample_im)
                var singleOrderTotalPrice=cartItem.sepetYemekPrice * cartItem.sepetYemekAmount
                totalPrice += singleOrderTotalPrice
                var amount = cartItem.sepetYemekAmount
                orderAmountTextView.text = amount.toString()
                orderNameTextView.text = cartItem.sepetYemekName
                orderTotalPrice.text = buildString {
                    append(singleOrderTotalPrice)
                    append(" ₺")
                }

                totalPriceListener(totalPrice)

                decreaseButton.setOnClickListener {

                    amount--
                    if (amount == 0) {
                        //Todo: Delete process here
                        cartList.removeAt(position)
                        notifyItemRemoved(position)
                    }
                        orderAmountTextView.text = amount.toString()
                        singleOrderTotalPrice=cartItem.sepetYemekPrice * amount
                        orderTotalPrice.text = buildString {
                            append(singleOrderTotalPrice)
                            append(" ₺")
                        }
                        cartItem.sepetYemekAmount = amount
                        updateTotalPrice()


                }
                increaseButton.setOnClickListener {

                    amount++
                    if (amount > 10) amount = 10
                    orderAmountTextView.text = amount.toString()

                    singleOrderTotalPrice=cartItem.sepetYemekPrice * amount
                    orderTotalPrice.text = buildString { append(singleOrderTotalPrice)
                        append(" ₺")
                    }
                    cartItem.sepetYemekAmount = amount

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
        println(currentCartItem.sepetYemekAmount)
        holder.bind(currentCartItem)
    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        for (item in cartList) {
            totalPrice += item.sepetYemekPrice * item.sepetYemekAmount
        }
        totalPriceListener(totalPrice)
    }

}