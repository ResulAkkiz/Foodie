package com.project.foodie.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.foodie.R
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.CartSingleItemBinding
import com.project.foodie.databinding.FavoriteSingleItemBinding

class FavoriteRecyclerViewAdapter(var favoriteList: ArrayList<Yemek>, var mContext: Context) :
    RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(var view: FavoriteSingleItemBinding) : ViewHolder(view.root) {
        fun bind(favoriteItem: Yemek) {

            with(view) {
                favoriteNameTextView.text=favoriteItem.yemekName
                favoritePrice.text=buildString {
                    append(favoriteItem.yemekPrice)
                    append(" ₺")
                }
                favoriteImageView.setImageResource(R.drawable.pizza_sample_im)
                favoriteDeleteButton.setOnClickListener {
                    favoriteList.removeAt(position)
                    notifyItemRemoved(position)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = FavoriteSingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = favoriteList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentFavorite = favoriteList[position]
        holder.bind(currentFavorite)
    }



}