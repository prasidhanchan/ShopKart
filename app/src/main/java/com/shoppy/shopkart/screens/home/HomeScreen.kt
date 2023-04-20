package com.shoppy.shopkart.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shoppy.shopkart.components.ShopKartAppBar
import com.shoppy.shopkart.components.SliderItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val userNameState = remember {
        mutableStateOf<String?>("")
    }

    val slidersList = remember {
        mutableStateOf<List<Any?>>(emptyList())
    }

    val errorState = remember {
        mutableStateOf("")
    }

    //getting username from firebase
    viewModel.getUserName {
        userNameState.value = it
    }

    viewModel.getSliders(except = {
        errorState.value = it
    }){
        slidersList.value = it
    }

//    val context = LocalContext.current

    Scaffold(topBar = { ShopKartAppBar(userNameState.value)}, modifier = Modifier.padding(top = 30.dp)) {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

            if (slidersList.value.isNotEmpty()) {
                SliderItem(slidersList = slidersList.value)
            }else{
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    CircularProgressIndicator(color = Color.Black)
                }
            }
        }
    }
}
