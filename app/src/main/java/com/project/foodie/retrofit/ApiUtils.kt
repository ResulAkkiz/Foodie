package com.project.foodie.retrofit

class ApiUtils {
    companion object{
        private const val baseUrl="http://kasimadalan.pe.hu/"

        fun getFoodieDao():FoodieDao{
            return RetrofitClient.getClient(baseUrl).create(FoodieDao::class.java)
        }
    }
}