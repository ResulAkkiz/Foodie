package com.project.foodie.di

import com.project.foodie.data.datasource.FoodieDataSource
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


}