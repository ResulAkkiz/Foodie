package com.project.foodie.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.firebase.FirebaseAuthInterface
import com.project.foodie.firebase.FirebaseAuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseAuthDataSource(private val firebaseAuthInstance: FirebaseAuth) :
    FirebaseAuthInterface {

    companion object {
        var error = ""
    }

    override suspend fun createNewUser(
        email: String,
        password: String,
        onError: (String) -> Unit,
    ): FirebaseUser? =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val authResult =
                    firebaseAuthInstance.createUserWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: FirebaseAuthException) {
                when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> {

                        error = "Bu e-posta adresi zaten kullanımda."
                        println("Bu e-posta adresi zaten kullanımda.")
                    }

                    "ERROR_WEAK_PASSWORD" -> {

                        error = "Zayıf şifre kullanıldı. Şifre en az 6 karakterden oluşmalıdır."
                        println("Zayıf şifre kullanıldı. Şifre en az 6 karakterden oluşmalıdır.")
                    }

                    else -> error = e.localizedMessage?.toString() ?: "Bir hata oluştu"
                }
                onError(error)
                null
            } catch (e: Exception) {
                error = e.localizedMessage.toString()

                onError(error)
                null
            }
        }


    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,

    ): FirebaseAuthResult? = withContext(Dispatchers.IO) {
        return@withContext try {
            val authResult =
                firebaseAuthInstance.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let { FirebaseAuthResult.Success(it) }
        } catch (e: FirebaseAuthException) {
            error = when (e.errorCode) {
                "ERROR_INVALID_CUSTOM_TOKEN" -> "Geçersiz özel belirteç."
                "ERROR_CUSTOM_TOKEN_MISMATCH" -> "Özel belirteç eşleşmiyor."
                "ERROR_INVALID_CREDENTIAL" -> "Geçersiz kimlik bilgisi."
                "ERROR_INVALID_EMAIL" -> "Geçersiz e-posta adresi."
                "ERROR_WRONG_PASSWORD" -> "Yanlış şifre."
                "ERROR_USER_MISMATCH" -> "Kullanıcı eşleşmiyor."
                "ERROR_REQUIRES_RECENT_LOGIN" -> "Yeniden giriş gerekiyor."
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "Farklı kimlik bilgisiyle var olan hesap."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Bu e-posta adresi zaten kullanımda."
                "ERROR_CREDENTIAL_ALREADY_IN_USE" -> "Bu kimlik bilgisi zaten kullanımda."
                "ERROR_USER_DISABLED" -> "Kullanıcı devre dışı bırakılmış durumda."
                "ERROR_USER_TOKEN_EXPIRED" -> "Kullanıcı belirteci süresi doldu."
                "ERROR_USER_NOT_FOUND" -> "Kullanıcı bulunamadı."
                "ERROR_INVALID_USER_TOKEN" -> "Geçersiz kullanıcı belirteci."
                "ERROR_OPERATION_NOT_ALLOWED" -> "İşlem izin verilmiyor."
                "ERROR_WEAK_PASSWORD" -> "Zayıf şifre kullanıldı. Şifre en az 6 karakterden oluşmalıdır."
                else -> "Bir hata oluştu: ${e.localizedMessage}"
            }
            FirebaseAuthResult.Failure(error)
        } catch (e: FirebaseAuthInvalidUserException) {
            error = when (e.errorCode) {
                "ERROR_USER_DISABLED" -> "Kullanıcı devre dışı bırakılmış durumda."
                "ERROR_USER_NOT_FOUND" ->  "Kullanıcı bulunamadı."
                else -> e.localizedMessage ?: "Bir hata oluştu."
            }
            FirebaseAuthResult.Failure(error)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            error = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" ->  "Geçersiz e-posta."
                "ERROR_WRONG_PASSWORD" -> "Yanlış şifre."
                else -> e.localizedMessage ?: "Bir hata oluştu."
            }
            FirebaseAuthResult.Failure(error)
        } catch (e: Exception) {
            error = e.localizedMessage ?: "Bir hata oluştu."
            FirebaseAuthResult.Failure(error)
        }
    }


    override suspend fun signOut(): Boolean {
        return try {
            firebaseAuthInstance.signOut()
            true
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun currentUser(): FirebaseUser? {
        return firebaseAuthInstance.currentUser
    }
}