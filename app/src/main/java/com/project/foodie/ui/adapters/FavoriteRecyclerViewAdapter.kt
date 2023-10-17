package com.project.foodie.ui.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.project.foodie.R
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.CartAlertDialogBinding
import com.project.foodie.databinding.CartSingleItemBinding
import com.project.foodie.databinding.DeleteConfirmDialogBinding
import com.project.foodie.databinding.FavoriteSingleItemBinding
import com.project.foodie.ui.fragments.FavoriteFragmentDirections
import com.project.foodie.ui.fragments.HomeFragmentDirections
import com.project.foodie.ui.viewmodels.FavoriteFragmentViewModel
import com.project.foodie.utils.getImage

class FavoriteRecyclerViewAdapter(var favoriteList: List<Yemek>, var mContext: Context,var viewModel:FavoriteFragmentViewModel) :
    RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(var view: FavoriteSingleItemBinding) : ViewHolder(view.root) {
        fun bind(favoriteItem: Yemek) {

            with(view) {
                root.setOnClickListener {
                    val direction= FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(favoriteItem)
                    Navigation.findNavController(it).navigate(direction)
                }
                favoriteNameTextView.text=favoriteItem.yemekName
                favoritePrice.text=buildString {
                    append(favoriteItem.yemekPrice)
                    append(" ₺")
                }
                Glide.with(mContext).load(favoriteItem.yemekPict.getImage()).into(favoriteImageView);
                favoriteDeleteButton.setOnClickListener {
                    showDeleteConfirmDialogBox(favoriteItem.yemekId)
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

    private fun showDeleteConfirmDialogBox(favoriteId:Int) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view =
            DeleteConfirmDialogBinding.inflate(LayoutInflater.from(mContext), null, false)
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.positiveButton.setOnClickListener {
            viewModel.deleteFavorite(favoriteId)
            dialog.dismiss()
        }
        view.negativeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



}