package com.project.foodie.data.repo

import com.project.foodie.data.datasource.FoodieDataSource

class FoodieRepository(var foodieDataSource: FoodieDataSource) {

    suspend fun insertCart(
        yemekName: String,
        yemekPict: String,
        yemekPrice: Int,
        yemekOrderAmount: Int,
        userName: String,
    ) =
        foodieDataSource.insertCart(yemekName, yemekPict, yemekPrice, yemekOrderAmount, userName)

    suspend fun getCartList(userName: String) =
        foodieDataSource.getCartList(userName)

    suspend fun getYemekler() = foodieDataSource.getYemekler()

    suspend fun deleteCartItem(yemekId: Int, userName: String) =
        foodieDataSource.deleteCartItem(yemekId, userName)

}