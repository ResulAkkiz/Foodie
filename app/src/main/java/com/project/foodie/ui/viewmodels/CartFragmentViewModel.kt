package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.foodie.data.entity.Sepet
import com.project.foodie.data.entity.Yemek
import com.project.foodie.data.repo.FoodieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class CartFragmentViewModel @Inject constructor(private var foodieRepository: FoodieRepository) :
    ViewModel() {
    val cartList = MutableLiveData<List<Sepet>>()
    val totalPrice=MutableLiveData<Int>()

    init {
        getCartList()
    }

    fun getCartList() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartList.value = foodieRepository.getCartList("Resul")
            }catch (e: Exception){
                cartList.value= listOf()
            }
            cartList.value?.let { calculateTotalPrice(it) }

        }
    }

    fun deleteCartItem(yemekId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodieRepository.deleteCartItem(yemekId,"Resul")
                getCartList()
            }catch (e: Exception){
                Log.e("HATA",e.message.toString())
            }

        }
    }

    fun calculateTotalPrice(yemekList:List<Sepet>){
        var totalPrice = 0
        for (item in yemekList) {
            totalPrice += item.sepetYemekPrice * item.sepetYemekOrderAmount
        }
        this.totalPrice.value=totalPrice
    }
}