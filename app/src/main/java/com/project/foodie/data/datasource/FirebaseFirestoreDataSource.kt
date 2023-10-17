package com.project.foodie.data.datasource

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.project.foodie.data.entity.User
import com.project.foodie.data.entity.Yemek
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

    override suspend fun getFavorites(userId: String): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val docSnapshot =
                    firebaseFirestoreInstance.collection("favorites").document(userId).get().await()
                if (docSnapshot.exists()) {
                    Log.e("TAG", "docsnapshot")
                    val data = docSnapshot.data
                    val favoriteList: List<Yemek>? = data?.map { singleMap ->
                        Log.e("List", singleMap.value.toString())
                        Yemek.fromMap(singleMap.value as Map<String, Any>)
                    }

                    Log.e("TAG", "Favorite List: ${favoriteList.toString()}")
                    FirebaseFirestoreResult.Success(favoriteList)
                } else {
                    FirebaseFirestoreResult.Failure("Bir kayıt bulunamadı")
                }
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun deleteFavorite(
        userId: String,
        favoriteId: Int,
    ): FirebaseFirestoreResult = withContext(Dispatchers.IO) {
        return@withContext try {
            firebaseFirestoreInstance.collection("favorites").document(userId)
                .update(mapOf(favoriteId.toString() to FieldValue.delete())).await()
            FirebaseFirestoreResult.Success(true)
        } catch (e: Exception) {
            error = e.localizedMessage
                ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        }
    }

    override suspend fun insertFavorite(userId: String, yemek: Yemek): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Log.e("TAG", yemek.toMap().toString())
                Log.e("TAG", userId)
                firebaseFirestoreInstance.collection("favorites").document(userId)
                    .set(mapOf(yemek.yemekId.toString() to yemek.toMap()), SetOptions.merge())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                Log.e("Error", error)
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun checkFavorited(userId: String, favoriteId: Int): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val documentSnapshot =
                    firebaseFirestoreInstance.collection("favorites").document(userId).get().await()
                val favoritesMap = documentSnapshot.data
                var yemekExists=false
                if (favoritesMap != null) {
                     yemekExists = favoritesMap.containsKey(favoriteId.toString())
                }
                FirebaseFirestoreResult.Success(yemekExists)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                Log.e("Error", error)
                FirebaseFirestoreResult.Failure(error)
            }
        }


}