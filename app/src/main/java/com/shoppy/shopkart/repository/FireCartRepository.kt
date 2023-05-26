package com.shoppy.shopkart.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireCartRepository @Inject constructor(private val queryCart: Query){

    suspend fun getCartFromFireBase(): DataOrException<List<MCart>,Boolean,Exception>{

        val dataOrException = DataOrException<List<MCart>,Boolean,Exception>()

        try {

            dataOrException.loading = true

            dataOrException.data = queryCart.get().await().documents.map { documentSnapshot ->

                documentSnapshot.toObject(MCart::class.java)!!
            }

            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        }catch (ex: FirebaseFirestoreException){
            dataOrException.e = ex
        }

        return dataOrException

    }
}