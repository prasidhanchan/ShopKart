package com.shoppy.shopkart.screens.myorderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MyOrderDetailsViewModel:ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun getAddressNamePhone(name:(String) -> Unit, phone:(String) -> Unit, address:(String) -> Unit){
        viewModelScope.launch {

            db.collection("Users").document(userId!!).get().addOnSuccessListener { address ->

                address(address.data?.getValue("address").toString())
                name(address.data?.getValue("name").toString())
                phone(address.data?.getValue("phone_no").toString())

            }
        }
    }

    fun cancelOrder(product_title: String){
        viewModelScope.launch {

            db.collection("Orders").document(userId + product_title).update("delivery_status","Cancelled")
        }

    }
}