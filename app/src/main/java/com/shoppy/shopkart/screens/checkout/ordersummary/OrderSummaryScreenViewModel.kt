package com.shoppy.shopkart.screens.checkout.ordersummary

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.repository.FireCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class OrderSummaryScreenViewModel @Inject constructor(private val cartRepository: FireCartRepository): ViewModel() {

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val dbOrders = FirebaseFirestore.getInstance().collection("Orders")
    private val dbCart = FirebaseFirestore.getInstance().collection("Cart")

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

    //Adding all List values
    fun sumValues(priceList: List<Int>, totalAmount: (Int) -> Unit) {

        //Delaying because values take time to load
        viewModelScope.launch {

            delay(100)
            val total = priceList.sum()
            totalAmount(total)
        }

    }

    fun getName(name: (String) -> Unit) {


        viewModelScope.launch {

//            email(FirebaseAuth.getInstance().currentUser?.email)
//            phone(FirebaseAuth.getInstance().currentUser?.phoneNumber)
            FirebaseFirestore.getInstance().collection("Users").document(userId!!).get().addOnSuccessListener { name ->
                name(name.data?.getValue("name").toString()) }
//            Log.d("EMAIIIL", "getEmailPhone: ${FirebaseAuth.getInstance().currentUser?.displayName}")
        }
    }

    //Uploading items along with payment method and delivery status to Orders collection and Deleting items from Cart collection
    fun uploadToOrdersAndDeleteCart(itemsList: List<MCart>,paymentMethod: String,deliveryStatus: String){

        viewModelScope.launch {

            for (mCart in itemsList){

                //Multiplying price with no of items and adding delivery fees
                val totProductPrice = mCart.product_price!! * mCart.item_count!! + 100

                dbOrders.document(userId + mCart.product_title).set(mCart)
                dbOrders.document(userId + mCart.product_title).update("product_price",totProductPrice)
                dbCart.document(userId + mCart.product_title).delete()

                //Uploading payment method to "Orders" collection
                dbOrders.document(userId + mCart.product_title).update("payment_method",paymentMethod)

                //Uploading delivery status to "Orders" collection
                dbOrders.document(userId + mCart.product_title).update("delivery_status",deliveryStatus)

                //Getting date using SimpleDateFormat()
                val simpleDate = SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH)
                val date = Date()
                val orderDate = simpleDate.format(date).toString()

                //Uploading date to "Orders" collection
                dbOrders.document(userId + mCart.product_title).update("order_date",orderDate)

                //Generating random numbers and uploading as Order Id
                val orderId = UUID.randomUUID().toString()
                dbOrders.document(userId + mCart.product_title).update("order_id",orderId)

            }
        }

    }

    fun uploadPaymentMethod(product_title: String,paymentMethod: String){

        viewModelScope.launch {

            dbOrders.document(userId + product_title).update("payment_method",paymentMethod)
        }
    }
}