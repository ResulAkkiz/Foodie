package com.project.foodie.firebase



sealed class FirebaseFirestoreResult {
    data class Success<T>(val data: T) : FirebaseFirestoreResult()
    data class Failure(val error: String) : FirebaseFirestoreResult()
}