package com.project.foodie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.entity.User
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FirebaseFirestoreRepository
import com.project.foodie.firebase.FirebaseAuthResult
import com.project.foodie.firebase.FirebaseFirestoreResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
) :
    ViewModel() {
    val getUserResult = MutableLiveData<FirebaseFirestoreResult>()
    var updateResult:FirebaseFirestoreResult?=null
    val signOutResult = MutableLiveData<Boolean>()

    companion object {
        var currentUser: FirebaseUser? = null
    }


    fun getUser() {
        CoroutineScope(Dispatchers.Main).launch {

            if (currentUser == null) {
                currentUser = firebaseAuthRepository.currentUser()
            }
            if (currentUser != null) {
                val result = firebaseFirestoreRepository.getUserById(currentUser!!.uid)
                getUserResult.value = result
            }
        }
    }

    fun updateUser(
        userId: String,
        map: Map<String, Any>,onComplete:()->Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("TAG", "updateUser")
            val result = firebaseFirestoreRepository.updateUser(userId, map)
            updateResult = result
            onComplete()
        }
    }

    fun signOut() {
        CoroutineScope(Dispatchers.Main).launch {
            signOutResult.value = firebaseAuthRepository.signOut()
        }
    }
}