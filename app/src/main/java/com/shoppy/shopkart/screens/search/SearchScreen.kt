package com.shoppy.shopkart.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ShopKartAppBar
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavHostController,viewModel: SearchScreenViewModel = hiltViewModel()) {

    val searchState = remember { mutableStateOf("") }
    
    val noResultState = remember { mutableStateOf("") }

    var noResultImg: Int? = null

    val searchResultList = rememberSaveable { mutableStateOf(emptyList<MProducts>()) }

    val keyBoardDown = LocalSoftwareKeyboardController.current

//    val isLoading = remember { mutableStateOf(false) }

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Search") }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

//        if (isLoading.value && searchResultList.value.isEmpty()) {

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                //Search Bar And Search Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    //Search Bar
                    SearchBox(
                        value = searchState.value,
                        onChange = searchState,
                        leadingIcon = R.drawable.ic_search
                    )

                    //Search Button
                    IconButton(modifier = Modifier
                        .size(55.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black),
                        onClick = {

                            //Showing nothing if no text is entered and search button is pressed
                            if (searchState.value == "") {

                                searchResultList.value = emptyList()

                            } else {

//                                isLoading.value = true

                                //Filtering Results by comparing with the Searched Text
                                searchResultList.value =
                                    viewModel.fireSearch.value.data?.toList()?.filter { mProducts ->

                                        //Comparing Product Title with Searched Text and Ignoring Case
                                        mProducts.product_title!!.contains(
                                            searchState.value,
                                            ignoreCase = true
                                        )
                                    }!!

                                //If searchResultList is not empty close loading
//                                if (searchResultList.value.isNotEmpty()) isLoading.value = false

                                //Showing No Results Found text if searchResultList is empty
                                if (searchResultList.value.isEmpty()) {
                                    noResultState.value = "No results found! Search something else"
                                    noResultImg = R.drawable.no_results_found
                                } else {
                                    noResultState.value = ""
                                    noResultImg = null
                                }

                                keyBoardDown?.hide()
                            }

                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }

                //Search Result Cards
                SearchResultCard(
                    searchResultList = searchResultList.value,
                    navController = navController
                )

                //No Results Found TextBox
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    if (noResultImg != null) Image(
                        painter = painterResource(id = noResultImg!!),
                        contentDescription = "No Results Found"
                    )
                    Text(
                        text = noResultState.value,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                }

//            PillButton(title = "Search", color = Color.Blue.toArgb()){
//
//                searchResultList.value = viewModel.fireSearch.value.data?.toList()?.filter { mProducts ->
//
//                    mProducts.product_title!!.contains(searchState.value, ignoreCase = true)
//
//                }!!
//            }

//            IconButton(modifier = Modifier.size(50.dp).clip(RoundedCornerShape(12.dp)).background(ShopKartUtils.blue), onClick = {
//
//                searchResultList.value = viewModel.fireSearch.value.data?.toList()?.filter { mProducts ->
//
//                    mProducts.product_title!!.contains(searchState.value, ignoreCase = true)
//
//                }!! }) {
//
//                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search")
//            }
            }
//        }else{
//            LoadingComp()
//        }
    }

//    AnimatedVisibility(visible = isLoading.value) {
//
//        LoadingComp()
//
//    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String = "Search Products",
    customAutoFocus: Boolean = true,
    onChange: MutableState<String>,
    leadingIcon: Int
) {
    //Text field Focus requester
    val focusReq = remember { FocusRequester() }

    TextField(
        value = value,
        onValueChange = { onChange.value = it },
        modifier = modifier
            .padding(10.dp)
            .width(280.dp)
            .focusRequester(focusReq),
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = value) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.White),
        textStyle = TextStyle(fontFamily = roboto),
        shape = RoundedCornerShape(10.dp),
        enabled = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        placeholder = { Text(text = placeHolder, color = Color.Black.copy(alpha = 0.4f), style = TextStyle(fontFamily = roboto)) }
    )

    if (customAutoFocus) {
        LaunchedEffect(Unit) {
            focusReq.requestFocus()
        }
    }
}

@Composable
fun SearchResultCard(searchResultList:List<MProducts>,navController: NavHostController){

    LazyColumn{
        items(items = searchResultList){ mProducts ->

            SearchResultCardItem(searchList = mProducts, navController = navController)

        }
    }
}

@Composable
fun SearchResultCardItem(searchList:MProducts,navController: NavHostController){

    //encoding url because / in url leads to problems
    val encodedUrl = URLEncoder.encode(searchList.product_url.toString(), StandardCharsets.UTF_8.toString())
    val encodedDescription = URLEncoder.encode(searchList.product_description.toString(), StandardCharsets.UTF_8.toString())
    //replacing + with a space
    val decodedDescription= encodedDescription.replace(oldValue = "+", newValue = " ")

    val encodedTitle = URLEncoder.encode(searchList.product_title.toString(), StandardCharsets.UTF_8.toString())
    val decodedTitle= encodedTitle.replace(oldValue = "+", newValue = " ")
    
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .height(150.dp)
        .clickable { navController.navigate(BottomNavScreens.Details.route + "/${encodedUrl}/${decodedTitle}/${decodedDescription}/${searchList.product_price}/${searchList.stock}/${searchList.category}/${searchList.product_id}") },
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp)) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

            Box(modifier = Modifier.size(115.dp)){
                AsyncImage(model = searchList.product_url, contentDescription = searchList.product_title)
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

                Text(text = searchList.product_title!!, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = searchList.product_description!!, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, fontFamily = roboto, color = Color.Black.copy(alpha = 0.5f)), maxLines = 3, overflow = TextOverflow.Ellipsis)
                Text(text = "₹${DecimalFormat("#,##,###").format(searchList.product_price!!.toDouble())}", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
            }

        }
    }
}

@Preview
@Composable
fun Prev(){

    val list = listOf(
        MProducts(product_title = "MacBook Pro", product_description = "MacBook Pro 2023", product_price = 100000),
        MProducts(product_title = "MacBook Pro", product_description = "MacBook Pro 2023", product_price = 100000),
        MProducts(product_title = "MacBook Pro", product_description = "MacBook Pro 2023", product_price = 100000),
        MProducts(product_title = "MacBook Pro", product_description = "MacBook Pro 2023", product_price = 100000)
    )

    SearchResultCard(searchResultList = list, navController = rememberNavController())
}