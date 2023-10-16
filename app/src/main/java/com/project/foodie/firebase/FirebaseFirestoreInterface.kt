package com.project.foodie.firebase

import com.project.foodie.data.entity.User

interface FirebaseFirestoreInterface {
    suspend fun getUser(userId: String): User?
    suspend fun updateUser(userID: String, map: Map<String, Any>): Boolean
    suspend fun saveUser(user: User): Boolean
}