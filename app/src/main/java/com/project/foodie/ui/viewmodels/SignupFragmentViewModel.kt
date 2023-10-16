package com.project.foodie.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.firebase.FirebaseAuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupFragmentViewModel @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository):
    ViewModel() {

    val firebaseUser = MutableLiveData<FirebaseUser?>()
    val exception = MutableLiveData<String?>()


    fun createNewUser(email: String, password: String){
        CoroutineScope(Dispatchers.Main).launch {
            val result=firebaseAuthRepository.createUserWithEmailAndPassword(email, password)
            when (result) {
                is FirebaseAuthResult.Success -> {
                    firebaseUser.value=result.data
                }
                is FirebaseAuthResult.Failure -> {
                    exception.value= result.error
                }
                else -> {

                }
            }
        }
    }
}