package com.shoppy.shopkart.repository

import androidx.compose.runtime.MutableState
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.Query
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireOrderRepository @Inject constructor(private val queryOrder: Query) {

    suspend fun getOrdersFromFirebase(): DataOrException<List<MCart>, Boolean, Exception>{

        val dataOrException = DataOrException<List<MCart>, Boolean, Exception>()

        try {

//            dataOrException.loading = true
            dataOrException.data = queryOrder.get().await().documents.map { orders ->

                orders.toObject(MCart::class.java)!!

            }

        }catch (ex: FirebaseException){
            dataOrException.e = ex
        }
        return dataOrException
    }

}