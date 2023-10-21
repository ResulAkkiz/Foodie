package com.project.foodie.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.foodie.data.entity.Sepet
import com.project.foodie.databinding.OrderSingleItemBinding

class OrderRecyclerViewAdapter(var orderList: List<Sepet>, var mContext: Context) :
    RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(var view: OrderSingleItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(orderItem: Sepet) {
            with(view) {
                orderNameText.text = orderItem.sepetYemekName
                orderAmountText.text = buildString {
                    append(orderItem.sepetYemekOrderAmount.toString())
                    append(" Adt.")
                }
                orderPriceText.text =
                    buildString {
                        append((orderItem.sepetYemekOrderAmount * orderItem.sepetYemekPrice).toString())
                        append(" ₺")
                    }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = OrderSingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = orderList[position]
        holder.bind(currentOrder)
    }


}