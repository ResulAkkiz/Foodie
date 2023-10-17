package com.project.foodie.data.repo


import com.project.foodie.data.datasource.FirebaseFirestoreDataSource
import com.project.foodie.data.entity.User

class FirebaseFirestoreRepository(private val firebaseFirestoreDataSource: FirebaseFirestoreDataSource) {
    suspend fun getUserById(
        userId: String,
    ) = firebaseFirestoreDataSource.getUserById(userId)


    suspend fun saveUser(
        user: User,
    ) = firebaseFirestoreDataSource.saveUser(user)

    suspend fun updateUser(
        userId: String,
        map: Map<String, Any>,
    ) = firebaseFirestoreDataSource.updateUser(userId,map)


}