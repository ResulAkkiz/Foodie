package com.project.foodie.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FirebaseFirestoreRepository
import com.project.foodie.firebase.FirebaseFirestoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
): ViewModel() {
    val resultGetFavorites = MutableLiveData<FirebaseFirestoreResult>()

    fun getFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = firebaseAuthRepository.currentUser()
            if (user != null) {
                val result=firebaseFirestoreRepository.getFavorites(userId = user.uid)
                resultGetFavorites.value=result
            }

        }
    }



    fun deleteFavorite(favoriteId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val user = firebaseAuthRepository.currentUser()
            if (user != null) {
                firebaseFirestoreRepository.deleteFavorite(user.uid,favoriteId)
                getFavorites()
            }

        }
    }
}