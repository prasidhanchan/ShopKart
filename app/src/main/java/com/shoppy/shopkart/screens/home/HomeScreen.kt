package com.shoppy.shopkart.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.ProductCard
import com.shoppy.shopkart.components.ShopKartAppBar2
import com.shoppy.shopkart.components.SliderItem
import com.shoppy.shopkart.models.MBrand
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    onAddToCart: (product: MProducts) -> Unit
) {
    val userNameState = remember { mutableStateOf<String?>("") }
    val imageState = remember { mutableStateOf<String?>("") }


    //Brand Logos
//    val brands = listOf(
//        R.drawable.apple_logo,
//        R.drawable.poco_logo,
//        R.drawable.samsung_logo,
//        R.drawable.google_logo,
//        R.drawable.sony_logo,
//        R.drawable.mi_logo,
//    )

    //getting username from firebase
    viewModel.getUserNameAndImage(profile_image = { imageState.value = it }) {
        userNameState.value = it
    }


    var brandList = emptyList<MBrand>()

    if (!viewModel.fireDataBrand.value.data.isNullOrEmpty()) {
        brandList = viewModel.fireDataBrand.value.data!!.toList()
    }

    val sliderData = viewModel.fireDataSlider.collectAsStateWithLifecycle().value
    var slidersList = sliderData.data?.toList()

    var listOfBestSeller = emptyList<MProducts>()

    if (!viewModel.fireDataBS.value.data.isNullOrEmpty()) {
        listOfBestSeller = viewModel.fireDataBS.value.data!!.toList()
    }

    var listOfMobilePhones = emptyList<MProducts>()

    if (!viewModel.fireDataMP.value.data.isNullOrEmpty()) {
        listOfMobilePhones = viewModel.fireDataMP.value.data!!.toList()
    }

    var listOfTv = emptyList<MProducts>()

    if (!viewModel.fireDataTv.value.data.isNullOrEmpty()) {
        listOfTv = viewModel.fireDataTv.value.data!!.toList()
    }

    var listOfEarphones = emptyList<MProducts>()

    if (!viewModel.fireDataEp.value.data.isNullOrEmpty()) {
        listOfEarphones = viewModel.fireDataEp.value.data!!.toList()
    }

    //Pull to Refresh Boolean
    val refreshing = viewModel.isLoading
    //Pull to Refresh State
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = {
            viewModel.pullToRefresh()
        }
    )

    Scaffold(
        topBar = {
            ShopKartAppBar2(
                userName = userNameState.value,
                profile_url = imageState.value,
                navHostController = navController
            ) {

                //Navigating to Search Screen
                navController.navigate(BottomNavScreens.SearchScreen.route)
            }
        },
        backgroundColor = ShopKartUtils.offWhite
    ) { innerPadding ->

        Box(
            modifier = Modifier.pullRefresh(state = refreshState),
            contentAlignment = Alignment.TopCenter
        ) {

            if (!refreshing.value) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (slidersList?.isNotEmpty() == true && sliderData.loading == false) {
                        SliderItem(slidersList = slidersList)

                        Text(
                            text = "Popular Brands",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, top = 25.dp)
                                .align(Alignment.Start)
                        )

                        //Brand Logos
                        BrandsList(brands = brandList)

                        Text(
                            text = "Best Seller",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, top = 20.dp)
                                .align(Alignment.Start)
                        )

                        ProductCard(
                            cardItem = listOfBestSeller,
                            navController = navController,
                            onAddToCartClick = onAddToCart
                        )

                        Divider(
                            modifier = Modifier
                                .width(300.dp)
                                .padding(top = 20.dp), thickness = 1.dp
                        )

                        Surface(
                            modifier = Modifier
                                .height(70.dp)
                                .width(180.dp)
                                .align(Alignment.Start)
                                .padding(start = 25.dp, top = 20.dp),
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Text(
                                    text = "Categories :",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = roboto
                                    ),
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                )
                            }
                        }

                        Text(
                            text = "Mobile Phones",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, top = 20.dp)
                                .align(Alignment.Start)
                        )

                        ProductCard(
                            cardItem = listOfMobilePhones,
                            navController = navController,
                            onAddToCartClick = onAddToCart
                        )

                        Text(
                            text = "Earphones",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, top = 35.dp)
                                .align(Alignment.Start)
                        )

                        ProductCard(
                            cardItem = listOfEarphones,
                            navController = navController,
                            onAddToCartClick = onAddToCart
                        )

                        Text(
                            text = "TVs",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, top = 35.dp)
                                .align(Alignment.Start)
                        )

                        ProductCard(
                            cardItem = listOfTv,
                            navController = navController,
                            onAddToCartClick = onAddToCart
                        )
                    } else if (slidersList.isNullOrEmpty() == true && sliderData.loading == false) {
                        Text(
                            text = if (viewModel.currentUser?.email?.contains("admin.") == true)
                                "Add some slider images"
                            else
                                "No slider images",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(top = 100.dp)
                        )
                    } else {
                        LoadingComp()
                    }

                    Spacer(modifier = Modifier.height(120.dp))
                }
                //This Column is required to Center the refresh Icon
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { }
            }
            PullRefreshIndicator(
                refreshing = refreshing.value,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }

    }
}

//Brand List LazyRow
@Composable
fun BrandsList(brands: List<MBrand>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)) {
        items(items = brands) { images ->
            BrandCard(brand = images)
        }
    }
}

//Single Card For Brands
@Composable
fun BrandCard(brand: MBrand) {
    Card(
        modifier = Modifier
            .height(58.dp)
            .width(88.dp)
            .padding(6.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {

        AsyncImage(
            model = brand.logo,
            contentDescription = brand.brand_name,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder)
        )

    }
}