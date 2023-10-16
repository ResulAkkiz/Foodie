package com.project.foodie.data.repo

import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.datasource.FirebaseAuthDataSource

class FirebaseAuthRepository(private val firebaseAuthDataSource: FirebaseAuthDataSource) {

    suspend fun createNewUser(
        email: String,
        password: String,
        onError: (String) -> Unit,
    ): FirebaseUser? {
        return firebaseAuthDataSource.createNewUser(email, password, onError)
    }


    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) = firebaseAuthDataSource.signInWithEmailAndPassword(email, password, )

    suspend fun signOut() = firebaseAuthDataSource.signOut()

    suspend fun currentUser() = firebaseAuthDataSource.currentUser()
}