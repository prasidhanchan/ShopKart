package com.shoppy.shopkart.screens.checkout.ordersummary

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.repository.FireCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderSummaryScreenViewModel @Inject constructor(private val cartRepository: FireCartRepository): ViewModel() {

    val fireSummary: MutableState<DataOrException<List<MCart>, Boolean, Exception>> = mutableStateOf(DataOrException(
        listOf(), true, Exception("")))

    init {
        getCartItems()
    }

    fun getCartItems(){

        viewModelScope.launch {

            fireSummary.value.loading = true

            fireSummary.value = cartRepository.getCartFromFireBase()

            if (!fireSummary.value.data.isNullOrEmpty()){
                fireSummary.value.loading = false
            }
        }
    }

    fun sumValues(priceList: List<Int>,totalAmount: (Int) -> Unit){

        viewModelScope.launch {

            delay(100)
            val total = priceList.sum()
            totalAmount(total)
        }

    }

}