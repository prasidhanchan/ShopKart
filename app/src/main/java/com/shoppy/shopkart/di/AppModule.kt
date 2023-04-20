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
    fun providesFireRepository()
    = FireRepository(queryProduct = FirebaseFirestore.getInstance().collection("Products"))
}