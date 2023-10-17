package com.project.foodie.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.project.foodie.data.entity.User

interface FirebaseFirestoreInterface {
    suspend fun getUserById(userId: String): FirebaseFirestoreResult
    suspend fun updateUser(userId: String, map: Map<String, Any>): FirebaseFirestoreResult
    suspend fun saveUser(user: User): FirebaseFirestoreResult
}