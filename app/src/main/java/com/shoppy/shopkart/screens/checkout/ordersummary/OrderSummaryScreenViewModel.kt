package com.shoppy.shopkart.screens.checkout.ordersummary

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.repository.FireCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class OrderSummaryScreenViewModel @Inject constructor(private val cartRepository: FireCartRepository): ViewModel() {

    val fireSummary: MutableState<DataOrException<List<MCart>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(
                listOf(), true, Exception("")
            )
        )

    init {
        getCartItems()
//        getEmailPhone()
    }

    fun getCartItems() {

        viewModelScope.launch {

            fireSummary.value.loading = true

            fireSummary.value = cartRepository.getCartFromFireBase()

            if (!fireSummary.value.data.isNullOrEmpty()) {
                fireSummary.value.loading = false
            }
        }
    }

    fun sumValues(priceList: List<Int>, totalAmount: (Int) -> Unit) {

        viewModelScope.launch {

            delay(100)
            val total = priceList.sum()
            totalAmount(total)
        }

    }

    fun getName(name: (String) -> Unit) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        viewModelScope.launch {

//            email(FirebaseAuth.getInstance().currentUser?.email)
//            phone(FirebaseAuth.getInstance().currentUser?.phoneNumber)
            FirebaseFirestore.getInstance().collection("Users").document(userId!!).get().addOnSuccessListener { name -> name(name.data?.getValue("name").toString()) }
//            Log.d("EMAIIIL", "getEmailPhone: ${FirebaseAuth.getInstance().currentUser?.displayName}")
        }
    }

    //Uploading items to Orders And Deleting From Cart
    fun uploadToOrdersAndDeleteCart(itemsList: List<MCart>){

        viewModelScope.launch {

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val dbOrders = FirebaseFirestore.getInstance().collection("Orders")
            val dbCart = FirebaseFirestore.getInstance().collection("Cart")

            for (mCart in itemsList){

                dbOrders.document(userId + mCart.product_title).set(mCart)

                dbCart.document(userId + mCart.product_title).delete()

            }
        }

    }
}