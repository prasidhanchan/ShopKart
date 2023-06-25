package com.shoppy.shopkart.screens.checkout.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AddressViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val currentUser = FirebaseAuth.getInstance().currentUser

    fun getAddressNamePhone(name:(String) -> Unit, phone:(String) -> Unit, address:(String) -> Unit){
        viewModelScope.launch {

            db.collection("Users").document(currentUser?.email!!).get().addOnSuccessListener { address ->

                address(address.data?.getValue("address").toString())
                name(address.data?.getValue("name").toString())
                phone(address.data?.getValue("phone_no").toString())

            }
        }
    }

    //Update address is only applicable to Users not Employees
    fun updateAddress(name: String,address: String,phone: String){

        viewModelScope.launch {

            db.collection("Users").document(currentUser?.email!!).update("name",name)
            db.collection("Users").document(currentUser.email!!).update("address",address)
            db.collection("Users").document(currentUser.email!!).update("phone_no",phone)
        }
    }
}