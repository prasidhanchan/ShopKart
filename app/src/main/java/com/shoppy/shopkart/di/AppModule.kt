package com.shoppy.shopkart.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.network.NotificationApi
import com.shoppy.shopkart.repository.FireAttendanceRepository
import com.shoppy.shopkart.repository.FireCartRepository
import com.shoppy.shopkart.repository.FireOrderRepository
import com.shoppy.shopkart.repository.FireOrderStatusRepository
import com.shoppy.shopkart.repository.FireRepository
import com.shoppy.shopkart.repository.FireSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFireRepositoryBrand()
    = FireRepository.FireRepositoryBrands(queryBrand = FirebaseFirestore.getInstance().collection("Brands"))

    @Singleton
    @Provides
    fun providesFireRepositorySlider()
    = FireRepository.FireRepositorySliders(querySlider = FirebaseFirestore.getInstance().collection("Sliders"))

    @Singleton
    @Provides
    fun providesFireRepositoryBestSeller()
    = FireRepository.FireRepositoryBestSeller(queryProduct = FirebaseFirestore.getInstance().collection("BestSeller"))

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
    fun providesFireRepositoryEarphones()
    = FireRepository.FireRepositoryEarphones(queryProduct = FirebaseFirestore.getInstance().collection("Earphones"))

    @Singleton
    @Provides
    fun providesGetCartFromFireBase()
    = FireCartRepository(
        queryCart = FirebaseFirestore.getInstance().collection("Cart")
            //sorting cart to display newest items first
        .orderBy("timestamp",Query.Direction.DESCENDING)
    )

    @Singleton
    @Provides
    fun providesGetOrdersFromFirebase()
    = FireOrderRepository(queryOrder = FirebaseFirestore.getInstance().collection("Orders")
        //sorting cart to display newest items first
        .orderBy("timestamp",Query.Direction.DESCENDING))

    @Singleton
    @Provides
    fun providesGetSearchResultFromFirebase()
    = FireSearchRepository(querySearch = FirebaseFirestore.getInstance().collection("AllProducts"))

    @Singleton
    @Provides
    fun providesGetOrderStatusFromFirebase()
    = FireOrderStatusRepository(queryStatus = FirebaseFirestore.getInstance().collection("Orders"))

    @Singleton
    @Provides
    fun providesGetEmployeeAttendanceFromFB()
    = FireAttendanceRepository(queryAttendance = FirebaseFirestore.getInstance().collection("Attendance"))

    //Notification API
    @Singleton
    @Provides
    fun providesPostNotification(): NotificationApi {
        return Retrofit.Builder()
            .baseUrl(ShopKartUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationApi::class.java)
    }
}
