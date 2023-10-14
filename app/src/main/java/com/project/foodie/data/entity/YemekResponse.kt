package com.project.foodie.data.entity

import com.google.gson.annotations.SerializedName

data class YemekResponse(
    @SerializedName("yemekler") var yemekList: List<Yemek>,
    @SerializedName("success") var success: Int,
)