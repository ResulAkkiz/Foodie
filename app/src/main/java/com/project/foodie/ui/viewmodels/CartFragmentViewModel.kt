package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.Yemek
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FoodieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    private var foodieRepository: FoodieRepository,
    private var firebaseAuthRepository: FirebaseAuthRepository,
) :
    ViewModel() {
    val cartList = MutableLiveData<List<Sepet>>()
    val totalPrice = MutableLiveData<Int>()

    init {
        getCartList()
    }

    companion object {
        var user: FirebaseUser? = null
    }

    fun getCartList() {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }
            if (user != null) {
                try {
                    cartList.value = foodieRepository.getCartList(user!!.uid)
                } catch (e: Exception) {
                    cartList.value = listOf()
                }
                cartList.value?.let { calculateTotalPrice(it) }
            }


        }
    }

    fun deleteCartItem(yemekId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (user == null) {
                user = firebaseAuthRepository.currentUser()
            }
            if (user!=null){
                try {
                    foodieRepository.deleteCartItem(yemekId, user!!.uid)
                    getCartList()
                } catch (e: Exception) {
                    Log.e("HATA", e.message.toString())
                }
            }


        }
    }

    fun calculateTotalPrice(yemekList: List<Sepet>) {
        var totalPrice = 0
        for (item in yemekList) {
            totalPrice += item.sepetYemekPrice * item.sepetYemekOrderAmount
        }
        this.totalPrice.value = totalPrice
    }
}