package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.entity.Sepet
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
class CartFragmentViewModel @Inject constructor(
    private var foodieRepository: FoodieRepository,
    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private var firebaseAuthRepository: FirebaseAuthRepository,
) :
    ViewModel() {
    val cartList = MutableLiveData<List<Sepet>>()
    val totalPrice = MutableLiveData<Int>()


    init {
        getCartList()
    }

    companion object {
        var firebaseUser: FirebaseUser? = null
    }

    fun getCartList() {
        CoroutineScope(Dispatchers.Main).launch {
            if (firebaseUser == null) {
                firebaseUser = firebaseAuthRepository.currentUser()
            }
            if (firebaseUser != null) {
                try {
                    cartList.value = foodieRepository.getCartList(firebaseUser!!.uid)
                } catch (e: Exception) {
                    cartList.value = listOf()
                }
                cartList.value?.let { calculateTotalPrice() }
            }


        }
    }

    fun deleteCartItem(yemekId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (firebaseUser == null) {
                firebaseUser = firebaseAuthRepository.currentUser()
            }
            if (firebaseUser != null) {
                try {
                    foodieRepository.deleteCartItem(yemekId, firebaseUser!!.uid)
                    getCartList()
                    calculateTotalPrice()
                } catch (e: Exception) {
                    Log.e("HATA", e.message.toString())
                }
            }


        }
    }

    fun insertCartItem(
        yemekId:Int,
        yemekName: String,
        yemekPict: String,
        yemekPrice: Int,
        yemekOrderAmount: Int,) {
        CoroutineScope(Dispatchers.Main).launch {
            if (firebaseUser == null) {
                firebaseUser = firebaseAuthRepository.currentUser()
            }
            if (firebaseUser != null) {
                try {
                    deleteCartItem(yemekId)
                    foodieRepository.insertCart(
                        yemekName, yemekPict, yemekPrice, yemekOrderAmount,
                        firebaseUser!!.uid
                    )
                    getCartList()
                    calculateTotalPrice()
                } catch (e: Exception) {
                    Log.e("HATA", e.message.toString())
                }
            }


        }
    }

    fun getUser(onComplete:(result:FirebaseFirestoreResult)->Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            if (firebaseUser == null) {
                firebaseUser = firebaseAuthRepository.currentUser()
            }
            if (firebaseUser != null) {
                try {
                   val result= firebaseFirestoreRepository.getUserById(firebaseUser!!.uid)
                    onComplete(result)
                } catch (e: Exception) {
                    Log.e("HATA", e.message.toString())
                }
            }


        }
    }


    private fun calculateTotalPrice() {
        var totalPrice = 0
        for (item in cartList.value!!) {
            totalPrice += item.sepetYemekPrice * item.sepetYemekOrderAmount
        }
        this.totalPrice.value = totalPrice
    }
}