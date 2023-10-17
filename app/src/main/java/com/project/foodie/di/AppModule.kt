package com.project.foodie.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.foodie.data.datasource.FirebaseAuthDataSource
import com.project.foodie.data.datasource.FirebaseFirestoreDataSource
import com.project.foodie.data.datasource.FoodieDataSource
import com.project.foodie.data.repo.FirebaseAuthRepository
import com.project.foodie.data.repo.FirebaseFirestoreRepository
import com.project.foodie.data.repo.FoodieRepository
import com.project.foodie.retrofit.ApiUtils
import com.project.foodie.retrofit.FoodieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodieRespository(foodieDataSource: FoodieDataSource): FoodieRepository {
        return FoodieRepository(foodieDataSource)
    }

    @Provides
    @Singleton
    fun provideFoodieDataSource(foodieDao: FoodieDao): FoodieDataSource {
        return FoodieDataSource(foodieDao)
    }

    @Provides
    @Singleton
    fun provideContactDao(): FoodieDao {
        return ApiUtils.getFoodieDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(firebaseAuthDataSource: FirebaseAuthDataSource): FirebaseAuthRepository {
        return FirebaseAuthRepository(firebaseAuthDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideFirebaseFirestoreRepository(firebaseFirestoreDataSource: FirebaseFirestoreDataSource): FirebaseFirestoreRepository {
        return FirebaseFirestoreRepository(firebaseFirestoreDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDataSource(firebaseFirestoreInstance: FirebaseFirestore): FirebaseFirestoreDataSource {
        return FirebaseFirestoreDataSource(firebaseFirestoreInstance)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


}