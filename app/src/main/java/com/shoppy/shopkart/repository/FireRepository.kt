package com.shoppy.shopkart.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MProducts
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryProduct: Query) {

    suspend fun getBestSellerFromFB(): DataOrException<List<MProducts>, Boolean, Exception> {

        val dataOrException = DataOrException<List<MProducts>, Boolean, Exception>()

        try {
            dataOrException.loading = true

            dataOrException.data = queryProduct.get().await().documents.map { documentSnapshot ->

//                Log.d("SNAPSHOT", "getAllProductsFromFB: ${documentSnapshot.toObject(MProducts::class.java)}")
                documentSnapshot.toObject(MProducts::class.java)!!

            }

            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        }catch (ex: FirebaseFirestoreException){
            dataOrException.e = ex
        }

        return dataOrException

    }

    class  FireRepositoryMobilePhones @Inject constructor(private val queryProduct: Query) {

        suspend fun getMobilePhonesFromFB(): DataOrException<List<MProducts>, Boolean, Exception> {

            val dataOrException = DataOrException<List<MProducts>, Boolean, Exception>()

            try {
                dataOrException.loading = true

                dataOrException.data =
                    queryProduct.get().await().documents.map { documentSnapshot ->

//                Log.d("SNAPSHOT", "getAllProductsFromFB: ${documentSnapshot.toObject(MProducts::class.java)}")
                        documentSnapshot.toObject(MProducts::class.java)!!

                    }

                if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

            } catch (ex: FirebaseFirestoreException) {
                dataOrException.e = ex
            }

            return dataOrException

        }
    }

    class  FireRepositoryTv @Inject constructor(private val queryProduct: Query) {

        suspend fun getTvFromFB(): DataOrException<List<MProducts>, Boolean, Exception> {

            val dataOrException = DataOrException<List<MProducts>, Boolean, Exception>()

            try {
                dataOrException.loading = true

                dataOrException.data =
                    queryProduct.get().await().documents.map { documentSnapshot ->

//                Log.d("SNAPSHOT", "getAllProductsFromFB: ${documentSnapshot.toObject(MProducts::class.java)}")
                        documentSnapshot.toObject(MProducts::class.java)!!

                    }

                if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

            } catch (ex: FirebaseFirestoreException) {
                dataOrException.e = ex
            }

            return dataOrException

        }
    }

    class  FireRepositoryRefrigerator @Inject constructor(private val queryProduct: Query) {

        suspend fun getRefrigeratorFromFB(): DataOrException<List<MProducts>, Boolean, Exception> {

            val dataOrException = DataOrException<List<MProducts>, Boolean, Exception>()

            try {
                dataOrException.loading = true

                dataOrException.data =
                    queryProduct.get().await().documents.map { documentSnapshot ->

//                Log.d("SNAPSHOT", "getAllProductsFromFB: ${documentSnapshot.toObject(MProducts::class.java)}")
                        documentSnapshot.toObject(MProducts::class.java)!!

                    }

                if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

            } catch (ex: FirebaseFirestoreException) {
                dataOrException.e = ex
            }

            return dataOrException

        }
    }
}