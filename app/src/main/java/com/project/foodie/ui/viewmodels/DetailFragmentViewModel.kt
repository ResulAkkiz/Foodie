package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.project.foodie.data.repo.FoodieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private var foodieRepository: FoodieRepository) :
    ViewModel() {

    fun insertCart(
        yemekName: String,
        yemekPict: String,
        yemekPrice: Int,
        yemekOrderAmount: Int,
        userName: String,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodieRepository.insertCart(
                    yemekName,
                    yemekPict,
                    yemekPrice,
                    yemekOrderAmount,
                    userName
                )
            } catch (e: Exception) {
                Log.e("HATA", e.message.toString())
            }

        }
    }
}