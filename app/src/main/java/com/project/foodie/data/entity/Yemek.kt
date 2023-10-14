package com.project.foodie.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Yemek(
    @SerializedName("yemek_id") var yemekId: Int,
    @SerializedName("yemek_adi") var yemekName: String,
    @SerializedName("yemek_resim_adi") var yemekPict: String,
    @SerializedName("yemek_fiyat") var yemekPrice: Int,
):Serializable
