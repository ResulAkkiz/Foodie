package com.project.foodie.data.entity

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("sepet_yemekler") var cartList: List<Sepet>,
    @SerializedName("success") var success: Int,
)