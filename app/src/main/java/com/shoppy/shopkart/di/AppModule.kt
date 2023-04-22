package com.shoppy.shopkart.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.repository.FireRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFireRepositoryBestSeller()
    = FireRepository(queryProduct = FirebaseFirestore.getInstance().collection("BestSeller"))

    @Singleton
    @Provides
    fun providesFireRepositoryMobilePhones()
    = FireRepository.FireRepositoryMobilePhones(queryProduct = FirebaseFirestore.getInstance().collection("MobilePhones"))

    @Singleton
    @Provides
    fun providesFireRepositoryTv()
        = FireRepository.FireRepositoryTv(queryProduct = FirebaseFirestore.getInstance().collection("Tv"))

    @Singleton
    @Provides
    fun providesFireRepositoryRefrigerator()
        = FireRepository.FireRepositoryRefrigerator(queryProduct = FirebaseFirestore.getInstance().collection("Refrigerator"))
}
