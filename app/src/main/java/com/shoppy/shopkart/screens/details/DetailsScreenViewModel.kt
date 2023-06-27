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
    val emailId = FirebaseAuth.getInstance().currentUser?.email

    fun uploadCartToFirebase(url: Any?,title: String?,description: String?,price: Int?,stock: Int?,category: String?,productId: String?){

        viewModelScope.launch {
           val cart = MCart(
                timestamp = timeStamp,
                item_count = 1,
                user_id = userId,
                product_url = url,
                product_title = title,
                product_description = description,
                product_price = price,
                stock = stock,
                category = category,
               product_id = productId
            ).convertToMap()

            db.collection("Cart").document(userId + title).set(cart)
        }
    }

    //Delete Product from AllProducts and category using their Category Name and Product ID
    fun deleteProduct(category: String, productId: String){
        viewModelScope.launch {
            db.collection(category).document(productId).delete()
            db.collection("AllProducts").document(productId).delete()
        }
    }
}