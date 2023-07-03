package com.shoppy.shopkart.screens.orders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MOrder
import com.shoppy.shopkart.repository.FireOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor(private val fireOrderRepository: FireOrderRepository): ViewModel() {

    val fireOrder: MutableState<DataOrException<List<MOrder>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        getOrdersFromFirebase()
    }

    private fun getOrdersFromFirebase(){

        viewModelScope.launch {

            fireOrder.value = fireOrderRepository.getOrdersFromFirebase()

            fireOrder.value.loading = false
        }
    }

    fun incrementNotificationCount(productTitle: String){

        viewModelScope.launch {

            var defaultCount: Int = 0

            FirebaseFirestore.getInstance().collection("Orders").document(userId + productTitle).get().addOnSuccessListener { docSnap ->
                defaultCount = (docSnap.data?.get("notificationCount").toString()).toInt()
            }.await()

            FirebaseFirestore.getInstance().collection("Orders").document(userId + productTitle).update("notificationCount", defaultCount + 1)
        }
    }
}