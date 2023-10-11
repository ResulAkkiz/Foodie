package com.project.foodie.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.project.foodie.R
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.YemeklerSingleItemBinding

class YemekRecyclerViewAdapter(var yemekList: List<Yemek>, var mContext: Context) :
    RecyclerView.Adapter<YemekRecyclerViewAdapter.YemekViewHolder>() {
    inner class YemekViewHolder(var view: YemeklerSingleItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(yemek: Yemek) {
            with(view) {
                root.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment)
                }
                nameTextView.text = yemek.yemekName
                priceTextView.text = buildString {
                    append(yemek.yemekPrice.toString())
                    append(" ₺")
                }
                yemekImageView.setImageResource(R.drawable.pizza_sample_im)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val view = YemeklerSingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return YemekViewHolder(view)
    }

    override fun getItemCount(): Int = yemekList.size

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        val currentYemek = yemekList[position]
        holder.bind(currentYemek)
    }
}