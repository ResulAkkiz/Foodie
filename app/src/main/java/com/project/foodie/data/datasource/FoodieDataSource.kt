package com.project.foodie.data.datasource

import android.util.Log
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.Yemek
import com.project.foodie.retrofit.FoodieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodieDataSource(var foodieDao: FoodieDao) {

    suspend fun insertCart(yemekName: String, yemekPict: String,yemekPrice:Int,yemekOrderAmount:Int,userName:String)= withContext(Dispatchers.IO) {
        val response=foodieDao.insertCart(yemekName,yemekPict,yemekPrice,yemekOrderAmount,userName)
        Log.e("Insert Contact", "Başarı: ${response.success}, message: ${response.message} ")
    }

    suspend fun deleteCartItem(yemekId: Int,userName:String) = withContext(Dispatchers.IO) {
        val response=foodieDao.deleteCartItem(yemekId,userName)
        Log.e("Insert Contact", "Başarı: ${response.success}, message: ${response.message} ")
    }

    suspend fun getCartList(userName: String): List<Sepet> = withContext(Dispatchers.IO) {
        return@withContext foodieDao.getCartList(userName).cartList
    }

    suspend fun getYemekler(): List<Yemek> = withContext(Dispatchers.IO) {
        return@withContext foodieDao.getYemekler().yemekList
    }


}