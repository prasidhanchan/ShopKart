package com.shoppy.shopkart.screens.admin

import android.icu.text.CaseMap.Title
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.models.MSliders
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdminScreenViewModel: ViewModel() {

    //Creating Slider folder in Firebase Storage with timestamp as image name
    private val storageRef = FirebaseStorage.getInstance().reference.child("Sliders").child(System.currentTimeMillis().toString())
    //Creating Product folder in Firebase Storage with timestamp as image name
    private val storageRef2 = FirebaseStorage.getInstance().reference.child("Products").child(System.currentTimeMillis().toString())

    private val db = FirebaseFirestore.getInstance()

    fun uploadSliderToStorageGetUrl(selectedImageUris: Uri?,taskDone: () -> Unit = {}) {

        viewModelScope.launch {

                storageRef.putFile(selectedImageUris!!).addOnSuccessListener {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        var sliders = MSliders(sliderUrl = uri).convertToMap()

                        db.collection("Sliders").add(sliders)
                    }
                }
            taskDone()

        }
    }

    fun uploadProductToStorageGetUrl(selectedImageUri: Uri?,title: String,price: String,desc: String,taskDone: () -> Unit) {

        viewModelScope.launch {

            if (selectedImageUri != null) {
                storageRef2.putFile(selectedImageUri).addOnSuccessListener {

                    storageRef2.downloadUrl.addOnSuccessListener { uri ->

                        val products = MProducts(productUrl = uri, productTitle = title, productPrice = price, productDesc = desc).convertToMap()

                        db.collection("Products").add(products)
                    }

                }
            }
            taskDone()

        }
    }
}