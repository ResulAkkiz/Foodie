package com.project.foodie.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Yemek(
    @SerializedName("yemek_id") var yemekId: Int,
    @SerializedName("yemek_adi") var yemekName: String,
    @SerializedName("yemek_resim_adi") var yemekPict: String,
    @SerializedName("yemek_fiyat") var yemekPrice: Int,
) : Serializable {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "yemek_id" to yemekId,
            "yemek_adi" to yemekName,
            "yemek_resim_adi" to yemekPict,
            "yemek_fiyat" to yemekPrice
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): Yemek {
            return Yemek(
                yemekId = (map["yemek_id"]  as Long).toInt(),
                yemekName = map["yemek_adi"] as String,
                yemekPict = map["yemek_resim_adi"] as String,
                yemekPrice = (map["yemek_fiyat"] as Long).toInt()
            )
        }
    }
}