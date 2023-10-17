package com.project.foodie.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.project.foodie.data.entity.User
import com.project.foodie.firebase.FirebaseFirestoreInterface
import com.project.foodie.firebase.FirebaseFirestoreResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreDataSource(private val firebaseFirestoreInstance: FirebaseFirestore) :
    FirebaseFirestoreInterface {

    companion object {
        var error = ""
    }

    override suspend fun getUserById(userId: String): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val docSnapshot =
                    firebaseFirestoreInstance.collection("users").document(userId).get().await()
                if (docSnapshot.exists()) {
                    val data = docSnapshot.data
                    val user = data.let { User.fromMap(data!!) }
                    FirebaseFirestoreResult.Success(user)
                } else {
                    FirebaseFirestoreResult.Failure(error)
                }
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun updateUser(
        userId: String,
        map: Map<String, Any>,
    ): FirebaseFirestoreResult = withContext(Dispatchers.IO) {
        return@withContext try {
            firebaseFirestoreInstance.collection("users").document(userId).update(map)
                .await()
            FirebaseFirestoreResult.Success(true)
        } catch (e: FirebaseFirestoreException) {
            error =
                e.localizedMessage ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        } catch (e: Exception) {
            error =
                e.localizedMessage ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        }
    }

    override suspend fun saveUser(user: User): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseFirestoreInstance.collection("users").document(user.userId)
                    .set(user.toMap())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (e: FirebaseFirestoreException) {
                error =
                    e.localizedMessage
                        ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            } catch (e: Exception) {
                error =
                    e.localizedMessage
                        ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }
}