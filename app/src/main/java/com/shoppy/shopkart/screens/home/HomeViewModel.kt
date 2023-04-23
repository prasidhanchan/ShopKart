package com.shoppy.shopkart.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.models.MSliders
import com.shoppy.shopkart.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireRepositorySlider: FireRepository.FireRepositorySliders,
                                        private val fireRepository: FireRepository.FireRepositoryBestSeller,
                                        private val fireRepository2: FireRepository.FireRepositoryMobilePhones,
                                        private val fireRepository3: FireRepository.FireRepositoryTv,
                                        private val fireRepository4: FireRepository.FireRepositoryEarphones): ViewModel() {

    //data with wrapper DataOrException
    val fireDataSlider: MutableState<DataOrException<List<MSliders>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))
    val fireDataBS: MutableState<DataOrException<List<MProducts>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))
    val fireDataMP: MutableState<DataOrException<List<MProducts>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))
    val fireDataTv: MutableState<DataOrException<List<MProducts>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))
    val fireDataEp: MutableState<DataOrException<List<MProducts>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getSlidersFromFB()
        getBestSellerFromFB()
        getMobilePhonesFromFB()
        getTvFromFB()
        getEarphonesFromFB()
    }

    fun getUserName(user: (String) -> Unit) {

        val mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser!!.uid

        FirebaseFirestore.getInstance().collection("Users").document(currentUser).get()
                .addOnSuccessListener { document ->
                    user(document.data!!.getValue("name").toString())
                }
    }

//    fun getSliders(except: (String) -> Unit,sliders: (List<Any?>) -> Unit) {
//
//        sliders(listOf(
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        ))
//
//        viewModelScope.launch {
//
//            try {
//                FirebaseFirestore.getInstance().collection("Sliders").document("sliders").get()
//                    .addOnSuccessListener { document ->
//                        sliders(document.data!!.values.toList())
//
////                        Log.d("TAGGS", "getSliders: ${document.toObject(MSliders::class.java)}")
//                        //TODO
//                    }
//            }catch (ex: Exception){
//                except(ex.message.toString())
//            }
//        }
//    }

    //Getting Sliders From Firebase
    private fun getSlidersFromFB(){

        viewModelScope.launch {
            fireDataSlider.value.loading = true
            fireDataSlider.value = fireRepositorySlider.getSlidersFromFB()

            if (!fireDataSlider.value.data.isNullOrEmpty()) fireDataSlider.value.loading = false

        }
//        Log.d("FIREDATA", "getRefrigeratorFromFB: ${fireDataRf.value.data?.toList()}")
    }

    //Getting Products from Firebase
    private fun getBestSellerFromFB(){

        viewModelScope.launch {
            fireDataBS.value.loading = true
            fireDataBS.value = fireRepository.getBestSellerFromFB()

            if (!fireDataBS.value.data.isNullOrEmpty()) fireDataBS.value.loading = false

        }
//        Log.d("FIREDATA", "getAllProductsFromFB: ${fireDataBS.value.data?.toList()}")
    }

    private fun getMobilePhonesFromFB(){

        viewModelScope.launch {
            fireDataMP.value.loading = true
            fireDataMP.value = fireRepository2.getMobilePhonesFromFB()

            if (!fireDataMP.value.data.isNullOrEmpty()) fireDataMP.value.loading = false

        }
//        Log.d("FIREDATA", "getMobilePhonesFromFB: ${fireDataMP.value.data?.toList()}")
    }

    private fun getTvFromFB(){

        viewModelScope.launch {
            fireDataTv.value.loading = true
            fireDataTv.value = fireRepository3.getTvFromFB()

            if (!fireDataTv.value.data.isNullOrEmpty()) fireDataTv.value.loading = false

        }
//        Log.d("FIREDATA", "getTvFromFB: ${fireDataTv.value.data?.toList()}")
    }

    private fun getEarphonesFromFB(){

        viewModelScope.launch {
            fireDataEp.value.loading = true
            fireDataEp.value = fireRepository4.getEarphonesFromFB()

            if (!fireDataEp.value.data.isNullOrEmpty()) fireDataEp.value.loading = false

        }
//        Log.d("FIREDATA", "getRefrigeratorFromFB: ${fireDataRf.value.data?.toList()}")
    }
}