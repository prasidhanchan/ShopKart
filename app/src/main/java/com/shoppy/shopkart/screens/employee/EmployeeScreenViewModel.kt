package com.shoppy.shopkart.screens.admin

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shoppy.shopkart.models.MBrand
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.models.MSliders
import kotlinx.coroutines.launch
import java.util.UUID

class EmployeeScreenViewModel: ViewModel() {

    //Creating Slider folder in Firebase Storage with timestamp as image name
    private val storageRef = FirebaseStorage.getInstance().reference.child("Sliders")
        .child(System.currentTimeMillis().toString())

    //Creating Product folder in Firebase Storage with timestamp as image name
    private val storageRef2 = FirebaseStorage.getInstance().reference.child("Products")
        .child(System.currentTimeMillis().toString())

    private val db = FirebaseFirestore.getInstance()

    fun uploadSliderToStorageGetUrl(selectedImageUris: Uri?, taskDone: () -> Unit = {}) {

        viewModelScope.launch {

            storageRef.putFile(selectedImageUris!!).addOnSuccessListener {

                storageRef.downloadUrl.addOnSuccessListener { uri ->

                    val sliders = MSliders(slider_image = uri).convertToMap()

                    db.collection("Sliders").add(sliders)
                }
            }
            taskDone()

        }
    }

    fun uploadProductToStorageGetUrl(
        selectedImageUri: Uri?,
        title: String,
        price: String,
        desc: String,
        stock: String,
        category : String,
        taskDone: () -> Unit
    ) {

        viewModelScope.launch {

            if (selectedImageUri != null) {

                val productId = UUID.randomUUID().toString()

                storageRef2.putFile(selectedImageUri).addOnSuccessListener {

                    storageRef2.downloadUrl.addOnSuccessListener { uri ->

                        val products = MProducts(
                            product_url = uri,
                            product_title = title,
                            product_price = price.toInt(),
                            product_description = desc,
                            stock = stock.toInt(),
                            category = category,
                            product_id = productId
                        ).convertToMap()

                        db.collection(category).document(productId).set(products)

                        //Do not upload to AllProducts if selected category is BestSeller
                        if (category == "MobilePhones" || category == "EarPhones" || category == "Tv") {
                            db.collection("AllProducts").add(products)
                        }
                    }
                }
            }
            taskDone()
        }
    }

    fun uploadBrand(selectedImageUri: Uri?,
                    brandName: String,
                    taskDone: () -> Unit){

            viewModelScope.launch {

                if (selectedImageUri != null) {
                    storageRef2.putFile(selectedImageUri).addOnSuccessListener {

                        storageRef2.downloadUrl.addOnSuccessListener { uri ->

                            val brands = MBrand(
                                logo = uri,
                                brand_name = brandName
                            ).convertToMap()

                            db.collection("Brands").add(brands)

                        }

                    }
                }
                taskDone()

            }
    }

    fun removeBrand(brandName: String){
        viewModelScope.launch {
            db.collection("Brands").document(brandName).delete()
        }
    }

//    fun deleteSliders() {
//
////        TODO Fix remove Slider
////        storageRef.child("Sliders").delete()
//        val docRef = db.collection("Sliders")
//        db.collection("Sliders").addSnapshotListener { value, error ->
//
//            value!!.documents.
//
////            doc.removeAt(0)
//            Log.d("DELETE", "deleteSliders: ${value.documents[0]}")
//        }
//
//        val snapshots = docRef.get()
//
//        for (doc in snapshots.result){
//            doc.reference.delete()
//        }

//        docRef.document().delete()

//        Log.d("DELETE", "deleteSliders: ${db.collection("Sliders").path}")
//        val updateValue = hashMapOf<String, Any>("slider_image" to FieldValue.delete())
//        docRef.update(updateValue)
//    }


//        fun deleteProduct() {

    //TODO Fix remove Product
//        storageRef.child("Sliders").delete()
//        val docRef = db.collection("Sliders").document("sliders").id

//        Log.d("ERRORS", "deleteSliders: ${db.collection("Sliders").document("sliders").id}")
//        val updateValue = hashMapOf<String,Any>("slider_image" to FieldValue.delete())

//        docRef.update(updateValue)
//        }
//    }
}