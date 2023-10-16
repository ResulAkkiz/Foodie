package com.project.foodie.firebase

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthResult {
    data class Success(val data: FirebaseUser) : FirebaseAuthResult()
    data class Failure(val error: String) : FirebaseAuthResult()
}