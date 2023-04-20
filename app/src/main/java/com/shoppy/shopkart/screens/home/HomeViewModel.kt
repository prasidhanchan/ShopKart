package com.shoppy.shopkart.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireRepository: FireRepository): ViewModel() {

    //data with wrapper DataOrException
    val fireData: MutableState<DataOrException<List<MProducts>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllProductsFromFB()
    }

    fun getUserName(user: (String) -> Unit) {

        val mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser!!.uid

        FirebaseFirestore.getInstance().collection("Users").document(currentUser).get()
                .addOnSuccessListener { document ->
                    user(document.data!!.getValue("name").toString())
                }
    }

    fun getSliders(except: (String) -> Unit,sliders: (List<Any?>) -> Unit) {

//        sliders(listOf(
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        ))

        viewModelScope.launch {

            try {
                FirebaseFirestore.getInstance().collection("Sliders").document("sliders").get()
                    .addOnSuccessListener { document ->
                        sliders(document.data!!.values.toList())

//                        Log.d("TAGGS", "getSliders: ${document.toObject(MSliders::class.java)}")
                        //TODO
                    }
            }catch (ex: Exception){
                except(ex.message.toString())
            }
        }
    }

    //Getting Products from Firebase
    private fun getAllProductsFromFB(){

        viewModelScope.launch {
            fireData.value.loading = true
            fireData.value = fireRepository.getAllProductsFromFB()

            if (!fireData.value.data.isNullOrEmpty()) fireData.value.loading = false

        }
        Log.d("FIREDATA", "getAllProductsFromFB: ${fireData.value.data?.toList()}")
    }
}