package com.project.foodie.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.project.foodie.data.repo.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository):ViewModel() {

}