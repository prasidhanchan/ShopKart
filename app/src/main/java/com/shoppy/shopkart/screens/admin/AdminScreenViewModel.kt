package com.shoppy.shopkart.screens.admin

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MAttendance
import com.shoppy.shopkart.models.MBrand
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.models.MSliders
import com.shoppy.shopkart.models.MUser
import com.shoppy.shopkart.repository.FireAttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdminScreenViewModel @Inject constructor(private val fireAttendanceRepository: FireAttendanceRepository): ViewModel() {

    //Creating Slider folder in Firebase Storage with timestamp as image name
    private val storageRef = FirebaseStorage.getInstance().reference.child("Sliders")
        .child(System.currentTimeMillis().toString())

    //Creating Product folder in Firebase Storage with timestamp as image name
    private val storageRef2 = FirebaseStorage.getInstance().reference.child("Products")
        .child(System.currentTimeMillis().toString())

    private val storageRefBrand = FirebaseStorage.getInstance().reference.child("Brands")
        .child(System.currentTimeMillis().toString())

    private val db = FirebaseFirestore.getInstance()

    private val mAuth = FirebaseAuth.getInstance()

    val fireAttendance: MutableState<DataOrException<List<MAttendance>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), false, Exception("")))

    init {
        getEmployeeAttendanceFromFB()
    }

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

    fun uploadBrand(selectedImageUri: Uri?,
                    brandName: String,
                    taskDone: () -> Unit){

        viewModelScope.launch {

            if (selectedImageUri != null) {
                storageRefBrand.putFile(selectedImageUri).addOnSuccessListener {

                    storageRefBrand.downloadUrl.addOnSuccessListener { uri ->

                        val brands = MBrand(
                            logo = uri,
                            brand_name = brandName
                        ).convertToMap()

                        db.collection("Brands").document(brandName).set(brands)

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

    fun uploadProductToStorageGetUrl(
        selectedImageUri: Uri?,
        title: String,
        price: String,
        desc: String,
        category : String,
        taskDone: () -> Unit
    ) {

        viewModelScope.launch {

            if (selectedImageUri != null) {
                storageRef2.putFile(selectedImageUri).addOnSuccessListener {

                    storageRef2.downloadUrl.addOnSuccessListener { uri ->

                        val products = MProducts(
                            product_url = uri,
                            product_title = title,
                            product_price = price.toInt(),
                            product_description = desc
                        ).convertToMap()

                        db.collection(category).add(products)

                        //Do not upload to AllProducts if selected category is BestSeller
                        if (category == "MobilePhones" || category == "EarPhones" || category == "Tvs") {
                            db.collection("AllProducts").add(products)
                        }
                    }

                }
            }
            taskDone()

        }
    }

    fun addEmployee(employee_name: String,employee_email: String,employee_password: String,employee_address: String,employee_phone: String,success:() -> Unit,errorCreateEmployee:(String) -> Unit){
        val empId = UUID.randomUUID().toString()
        viewModelScope.launch {
            mAuth.createUserWithEmailAndPassword(employee_email,employee_password).addOnSuccessListener {
                //Sign Out employee account and sign in Admin account
                mAuth.signOut()
                adminLogin()
                success()
            }.addOnFailureListener{ error ->
                errorCreateEmployee(error.message.toString())
            }

            val employee = MUser(id = empId, name = employee_name,email = employee_email, password = employee_password, address = employee_address, phone_no = employee_phone, profile_image = "")
            val attendance = MAttendance(id = empId, name = employee_name,email = employee_email, address = employee_address, phone_no = employee_phone)
            db.collection("Employees").document(empId).set(employee)
            db.collection("Attendance").document(empId).set(attendance)
        }
    }

    private fun getEmployeeAttendanceFromFB(){
        viewModelScope.launch {
            fireAttendance.value = fireAttendanceRepository.getEmployeeAttendanceFromFB()
        }
    }

    fun presentOrAbsent(PAB: String,orderId: String,Day: String){
        db.collection("Attendance").document(orderId).update("day$Day",PAB)
    }

    private fun adminLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword("admin.kawaki@gmail.com","kawaki22")
    }

    //Clear all day fields in all documents using for loop
    fun clearAttendance(context:Context){
        //limiting to avoid out of memory errors
        val limit = 100
        db.collection("Attendance").limit(limit.toLong()).get().addOnCompleteListener { docSnap ->

            for ( doc in docSnap.result.documents){
                doc.reference.update("day01","",
                    "day02","",
                    "day03","",
                    "day04","",
                    "day05","",
                    "day06","",
                    "day07","",
                    "day08","",
                    "day09","",
                    "day10","",
                    "day11","",
                    "day12","",
                    "day13","",
                    "day14","",
                    "day15","",
                    "day16","",
                    "day17","",
                    "day18","",
                    "day19","",
                    "day20","",
                    "day21","",
                    "day22","",
                    "day23","",
                    "day24","",
                    "day25","",
                    "day26","",
                    "day27","",
                    "day28","",
                    "day29","",
                    "day30","",
                    "day31","",
                )
            }
            //Showing toast on task complete
        }.addOnSuccessListener { Toast.makeText(context,"Attendance Cleared",Toast.LENGTH_SHORT).show() }
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