package com.project.foodie.retrofit

import com.project.foodie.data.entity.CRUDResponse
import com.project.foodie.data.entity.CartResponse
import com.project.foodie.data.entity.YemekResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodieDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getYemekler(): YemekResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun insertCart(
        @Field("yemek_adi") yemekName: String,
        @Field("yemek_resim_adi") yemekPict: String,
        @Field("yemek_fiyat") yemekPrice: Int,
        @Field("yemek_siparis_adet") yemekOrderAmount: Int,
        @Field("kullanici_adi") userName: String,
    ): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getCartList(
        @Field("kullanici_adi") userName: String,
    ): CartResponse


    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteCartItem(
        @Field("sepet_yemek_id") yemekId: Int,
        @Field("kullanici_adi") userName: String
    ): CRUDResponse


}