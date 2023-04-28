package com.shoppy.shopkart.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.models.MCart
import kotlinx.coroutines.launch

class DetailsScreenViewModel :ViewModel(){

    private val db = FirebaseFirestore.getInstance()

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val timeStamp = System.currentTimeMillis().toString()

    fun uploadCartToFirebase(url: Any?,title: String?,description: String?,price: Int?){

//        Log.d("FIREUSER", "uploadCartToFirebase: $userId")
        viewModelScope.launch {
           val cart = MCart(
                timestamp = timeStamp,
                item_count = 1,
                user_id = userId,
                product_url = url,
                product_title = title,
                product_description = description,
                product_price = price
            ).convertToMap()

            db.collection("Cart").document(userId + title).set(cart)
//            db.collection("Users").document(userId!!).update(
//                "user_cart", cart)
//            Log.d("CARTSS", "uploadCartToFirebase: ${userId}${title}")
        }
    }
}