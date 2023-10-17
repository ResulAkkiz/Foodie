package com.project.foodie.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.foodie.data.entity.User
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FirebaseFirestoreRepository
import com.project.foodie.firebase.FirebaseAuthResult
import com.project.foodie.firebase.FirebaseFirestoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
) :
    ViewModel() {

    val firebaseUser = MutableLiveData<FirebaseUser?>()
    val exception = MutableLiveData<String?>()


    fun createNewUser(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = firebaseAuthRepository.createUserWithEmailAndPassword(email, password)
            when (result) {
                is FirebaseAuthResult.Success -> {
                    val saveResult=firebaseFirestoreRepository.saveUser(
                        User(
                            result.data.uid, "", "", "", "",
                        )
                    )
                    when (saveResult) {
                        is FirebaseFirestoreResult.Success<*> -> {
                            firebaseUser.value = result.data
                        }

                        is FirebaseFirestoreResult.Failure -> {
                            exception.value = saveResult.error
                        }

                    }

                }

                is FirebaseAuthResult.Failure -> {
                    exception.value = result.error
                }

                else -> {

                }
            }
        }
    }
}