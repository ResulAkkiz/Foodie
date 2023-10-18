package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.User
import com.project.foodie.data.entity.Yemek
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FirebaseFirestoreRepository
import com.project.foodie.data.repo.FoodieRepository
import com.project.foodie.firebase.FirebaseFirestoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private var foodieRepository: FoodieRepository,
    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private var firebaseAuthRepository: FirebaseAuthRepository,
) :
    ViewModel() {

    companion object {
        var user: FirebaseUser? = null
    }


    val isFavorite = MutableLiveData<FirebaseFirestoreResult>()

    fun insertCart(
        yemekName: String,
        yemekPict: String,
        yemekPrice: Int,
        yemekOrderAmount: Int,

        ) {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }
            if (user != null) {
                try {

                    val cartList = try {
                        foodieRepository.getCartList(user!!.uid)
                    } catch (e: Exception) {
                        listOf()
                    }


                    val cartItemFiltered = cartList.firstOrNull { it.sepetYemekName == yemekName }

                    if (cartItemFiltered != null) {

                        foodieRepository.deleteCartItem(cartItemFiltered.sepetYemekId, user!!.uid)

                        foodieRepository.insertCart(
                            yemekName,
                            yemekPict,
                            yemekPrice,
                            yemekOrderAmount + cartItemFiltered.sepetYemekOrderAmount,
                            user!!.uid
                        )

                    } else {

                        foodieRepository.insertCart(
                            yemekName,
                            yemekPict,
                            yemekPrice,
                            yemekOrderAmount,
                            user!!.uid
                        )
                    }

                } catch (e: Exception) {
                    Log.e("HATA", e.message.toString())
                }
            }


        }
    }


    fun insertFavorite(
        yemek: Yemek,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }

            if (user != null) {
                firebaseFirestoreRepository.insertFavorite(user!!.uid, yemek)
                checkFavorited(yemek.yemekId)
            }

        }
    }

    fun checkFavorited(favoriteId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }
            if (user != null) {
                val result = firebaseFirestoreRepository.checkFavorited(user!!.uid, favoriteId)
                isFavorite.value = result
            }

        }
    }

    fun deleteFavorite(favoriteId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }

            if (user != null) {
                firebaseFirestoreRepository.deleteFavorite(user!!.uid, favoriteId)
                checkFavorited(favoriteId)
            }

        }
    }
}