package com.shoppy.shopkart.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shoppy.shopkart.components.ProductCard
import com.shoppy.shopkart.components.ShopKartAppBar
import com.shoppy.shopkart.components.SliderItem
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()) {

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

    var listOfBestSeller = emptyList<MProducts>()

    if (!viewModel.fireDataBS.value.data.isNullOrEmpty()){
        listOfBestSeller = viewModel.fireDataBS.value.data!!.toList()

            //TODO display products
//            Log.d("MPRODUCT", "HomeScreen: ${product.product_title}")
    }


    var listOfMobilePhones = emptyList<MProducts>()

    if (!viewModel.fireDataMP.value.data.isNullOrEmpty()){
        listOfMobilePhones = viewModel.fireDataMP.value.data!!.toList()

        //TODO display products
//            Log.d("MPRODUCTMP", "HomeScreen: $listOfMobilePhones")
    }

    var listOfTv = emptyList<MProducts>()

    if (!viewModel.fireDataTv.value.data.isNullOrEmpty()){
        listOfTv = viewModel.fireDataTv.value.data!!.toList()

        //TODO display products
//            Log.d("MPRODUCTv", "HomeScreen: $listOfTv")
    }

    var listOfRefrigerator = emptyList<MProducts>()

    if (!viewModel.fireDataRf.value.data.isNullOrEmpty()){
        listOfRefrigerator = viewModel.fireDataRf.value.data!!.toList()

        //TODO display products
//            Log.d("MPRODUCTv", "HomeScreen: $listOfTv")
    }

//    val context = LocalContext.current

    Scaffold(topBar = { ShopKartAppBar(userNameState.value)},
        modifier = Modifier.padding(top = 30.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

            if (slidersList.value.isNotEmpty() && viewModel.fireDataBS.value.loading == false) {
                SliderItem(slidersList = slidersList.value)

                Divider(modifier = Modifier.width(320.dp)
                    .padding(top = 25.dp),color = Color.Black.copy(alpha = 0.2f), thickness = 2.dp)

                Text(text = "Best Seller", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 25.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfBestSeller){navController.navigate(NavScreens.DetailsScreen.name)}

                Text(text = "Categories :", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                Text(text = "Mobile Phones", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 20.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfMobilePhones){navController.navigate(NavScreens.DetailsScreen.name)}

                Text(text = "TVs", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfTv){navController.navigate(NavScreens.DetailsScreen.name)}

                Text(text = "Refrigerators", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfRefrigerator){navController.navigate(NavScreens.DetailsScreen.name)}
            }else{
               LoadingComp()
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun LoadingComp() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(250.dp))

        CircularProgressIndicator(color = Color.Black)
    }
}
