package com.shoppy.shopkart.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartColors
import com.shoppy.shopkart.components.ProductCard
import com.shoppy.shopkart.components.ShopKartAppBar
import com.shoppy.shopkart.components.SliderItem
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.models.MSliders
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()) {

    val userNameState = remember {
        mutableStateOf<String?>("")
    }


    //Brand Logos
    val brands = listOf(
        R.drawable.apple_logo,
        R.drawable.poco_logo,
        R.drawable.samsung_logo,
        R.drawable.google_logo,
        R.drawable.sony_logo,
        R.drawable.mi_logo,

    )

    //getting username from firebase
    viewModel.getUserName {
        userNameState.value = it
    }
    

    var slidersList = emptyList<MSliders>()

    if (!viewModel.fireDataSlider.value.data.isNullOrEmpty()){
        slidersList = viewModel.fireDataSlider.value.data!!.toList()
    }

    var listOfBestSeller = emptyList<MProducts>()

    if (!viewModel.fireDataBS.value.data.isNullOrEmpty()){
        listOfBestSeller = viewModel.fireDataBS.value.data!!.toList()
    }


    var listOfMobilePhones = emptyList<MProducts>()

    if (!viewModel.fireDataMP.value.data.isNullOrEmpty()){
        listOfMobilePhones = viewModel.fireDataMP.value.data!!.toList()
    }

    var listOfTv = emptyList<MProducts>()

    if (!viewModel.fireDataTv.value.data.isNullOrEmpty()){
        listOfTv = viewModel.fireDataTv.value.data!!.toList()
    }

    var listOfEarphones = emptyList<MProducts>()

    if (!viewModel.fireDataEp.value.data.isNullOrEmpty()){
        listOfEarphones = viewModel.fireDataEp.value.data!!.toList()
    }

//    val context = LocalContext.current

    Scaffold(topBar = { ShopKartAppBar(userNameState.value)},
//        modifier = Modifier.padding(top = 10.dp),
    backgroundColor = ShopKartColors.offWhite) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

            if (slidersList.isNotEmpty() && viewModel.fireDataBS.value.loading == false) {
                SliderItem(slidersList = slidersList)

//                Divider(modifier = Modifier.width(320.dp)
//                    .padding(top = 25.dp),color = Color.Black.copy(alpha = 0.2f), thickness = 2.dp)

                Text(text = "Popular Brands", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 25.dp)
                        .align(Alignment.Start))
                
                BrandsList(brands = brands)
                

                Text(text = "Best Seller", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 25.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfBestSeller, navController = navController)

                Text(text = "Categories :", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                Text(text = "Mobile Phones", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 20.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfMobilePhones,navController = navController)

                Text(text = "Earphones", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfEarphones,navController = navController)

                Text(text = "TVs", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 35.dp)
                        .align(Alignment.Start))

                ProductCard(cardItem = listOfTv,navController = navController)
            }else{
               LoadingComp()
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

//Brand List LazyROw
@Composable
fun BrandsList(brands: List<Int>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)){
        items(items = brands){ images ->
            BrandCard(brandImage = images)
        }
    }
}

//Single Card For Brands
@Composable
fun BrandCard(brandImage: Int) {
    Card(modifier = Modifier
        .height(58.dp)
        .width(88.dp)
        .padding(6.dp),
        elevation = 2.dp,
    shape = RoundedCornerShape(12.dp)
    ) {
        
        Image(painter = painterResource(id = brandImage ), contentDescription = "brands", contentScale = ContentScale.Crop)
        
    }
}


//Progress Bar
@Composable
fun LoadingComp() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(250.dp))

        CircularProgressIndicator(color = Color.Black)
    }
}
