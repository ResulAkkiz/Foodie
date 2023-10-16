package com.project.foodie.data.entity

data class User(
    var userId: String,
    var userName: String,
    var userSurname: String,
    var userPhoneNumber: String,
    var userAdress: String,
    var userFavoriteList: List<Yemek>,
)
