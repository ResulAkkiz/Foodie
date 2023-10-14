package com.project.foodie.data.entity

import com.google.gson.annotations.SerializedName

data class Sepet(
    @SerializedName("sepet_yemek_id") var sepetYemekId: Int,
    @SerializedName("yemek_adi") var sepetYemekName: String,
    @SerializedName("yemek_resim_adi") var sepetYemekPict: String,
    @SerializedName("yemek_fiyat") var sepetYemekPrice: Int,
    @SerializedName("yemek_siparis_adet") var sepetYemekOrderAmount: Int,
    @SerializedName("kullanici_adi") var userName: String,
)