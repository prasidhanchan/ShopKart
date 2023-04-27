package com.shoppy.shopkart.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
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

                documentSnapshot.data?.getValue("product_price")

//                Log.d("SNAPSHOT", "getAllProductsFromFB: ${documentSnapshot.toObject(MCart::class.java)}")

                documentSnapshot.toObject(MCart::class.java)!!
            }

            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        }catch (ex: FirebaseFirestoreException){
            dataOrException.e = ex
        }

        return dataOrException

    }

    suspend fun getPrice(firePrice: (String) -> Unit) {

//        val dataOrException = DataOrException<List<MCart>,Boolean,Exception>()


//            dataOrException.loading = true

//            dataOrException.data =
        queryCart.get().await().documents.map { documentSnapshot ->

            firePrice(documentSnapshot.data?.getValue("product_price").toString())
            Log.d("FIREPRICE", "getPrice: ${documentSnapshot.data?.getValue("product_price")}")
        }
    }

}