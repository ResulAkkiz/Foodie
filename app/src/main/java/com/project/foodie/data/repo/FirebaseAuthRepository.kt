package com.project.foodie.data.repo

import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.datasource.FirebaseAuthDataSource

class FirebaseAuthRepository(private val firebaseAuthDataSource: FirebaseAuthDataSource) {

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ) = firebaseAuthDataSource.createUserWithEmailAndPassword(email, password)


    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) = firebaseAuthDataSource.signInWithEmailAndPassword(email, password)

    suspend fun signOut() = firebaseAuthDataSource.signOut()

    suspend fun currentUser() = firebaseAuthDataSource.currentUser()
}