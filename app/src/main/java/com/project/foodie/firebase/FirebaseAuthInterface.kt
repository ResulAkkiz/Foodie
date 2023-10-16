package com.project.foodie.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthInterface {


    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseAuthResult?

    suspend fun signOut(): Boolean

    suspend fun currentUser(): FirebaseUser?
    suspend fun createNewUser(
        email: String,
        password: String,
        onError: (String) -> Unit
    ): FirebaseUser?
}