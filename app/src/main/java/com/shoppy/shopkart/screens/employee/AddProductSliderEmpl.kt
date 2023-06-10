package com.shoppy.shopkart.screens.employee

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.GalleryLaunchComp
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.SelectedImageItem
import com.shoppy.shopkart.components.TextBox2
import com.shoppy.shopkart.screens.admin.EmployeeScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddProductSliderEmpl(navHostController: NavHostController,viewModel: EmployeeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {


    val context = LocalContext.current

    val selectedSliderImageUri = remember { mutableStateOf<Uri?>(null) }

    val selectedProductImageUri = remember { mutableStateOf<Uri?>(null) }

    val launchGallerySlider =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedSliderImageUri.value = uri })

    val launchGalleryProduct =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedProductImageUri.value = uri })

    val productTitle = remember { mutableStateOf("") }

    val productPrice = remember { mutableStateOf("") }

    val productDescription = remember { mutableStateOf("") }

    val stock = remember { mutableStateOf("") }

    //DropDown State
    val isExpanded = remember { mutableStateOf(false) }

    //Dropdown selected option
    val selectedOption = remember { mutableStateOf("Select Category") }

    Scaffold(
        topBar = {
            //Back Button
            BackButton(navController = navHostController, topBarTitle = "Add Product/Slider", spacing = 30.dp)
        },
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = ShopKartUtils.offWhite
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            //Upload Sliders text
            Text(
                text = "Upload Slider", modifier = Modifier.padding(start = 30.dp, top = 20.dp),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                )
            )

            GalleryLaunchComp(title = "Select Slider", color = Color.Black.copy(alpha = 0.1f)) {
//                launchGallerySlider.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                launchGallerySlider.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }


            if (selectedSliderImageUri.value != null) SelectedImageItem(uris = selectedSliderImageUri.value)

            PillButton(
                title = "Post Slider",
                color = ShopKartUtils.black.toInt(),
                modifier = Modifier
                    .padding(bottom = 20.dp, top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {

                if (selectedSliderImageUri.value != null) {
                    viewModel.uploadSliderToStorageGetUrl(selectedImageUris = selectedSliderImageUri.value) {
                        selectedSliderImageUri.value = null
                        navHostController.popBackStack()
                        Toast.makeText(context, "Slider Uploaded", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Select an Image", Toast.LENGTH_SHORT).show()
                }
            }

            Divider()

            //Upload Product text
            Text(
                text = "Upload Product", modifier = Modifier.padding(start = 30.dp, top = 20.dp),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                )
            )

            //Upload Products
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GalleryLaunchComp(
                    title = "Select Product Image",
                    color = Color.Black.copy(0.1f)
                ) {
                    launchGalleryProduct.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }

                if (selectedProductImageUri.value != null) SelectedImageItem(uris = selectedProductImageUri.value)

//                Box(modifier = Modifier.fillMaxWidth()
//                    .height(50.dp)) {

                ExposedDropdownMenuBox(
                    expanded = isExpanded.value,
                    onExpandedChange = { isExpanded.value = it }) {

                    TextField(
                        value = selectedOption.value, onValueChange = {},
                        readOnly = true, textStyle = TextStyle(fontFamily = roboto),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded.value,
                        onDismissRequest = { isExpanded.value = false }) {

                        DropdownMenuItem(onClick = {
                            selectedOption.value = "BestSeller"
                            isExpanded.value = false
                        }) {

                            Text(text = "Best Seller", style = TextStyle(fontFamily = roboto))
                        }

                        DropdownMenuItem(onClick = {
                            selectedOption.value = "MobilePhones"
                            isExpanded.value = false
                        }) {

                            Text(text = "Mobile Phones", style = TextStyle(fontFamily = roboto))
                        }

                        DropdownMenuItem(onClick = {
                            selectedOption.value = "Tv"
                            isExpanded.value = false
                        }) {

                            Text(text = "Tv", style = TextStyle(fontFamily = roboto))
                        }

                        DropdownMenuItem(onClick = {
                            selectedOption.value = "Earphones"
                            isExpanded.value = false
                        }) {

                            Text(text = "Earphones", style = TextStyle(fontFamily = roboto))
                        }

                    }

                }

                TextBox2(
                    value = productTitle.value,
                    onChange = productTitle,
                    placeHolder = "Title",
                    keyBoardType = KeyboardType.Text
                )

                TextBox2(
                    value = productPrice.value,
                    onChange = productPrice,
                    placeHolder = "Price",
                    keyBoardType = KeyboardType.Number
                )

                TextBox2(
                    value = productDescription.value,
                    onChange = productDescription,
                    placeHolder = "Description",
                    keyBoardType = KeyboardType.Text
                )

                TextBox2(
                    value = stock.value,
                    onChange = stock,
                    placeHolder = "Stock",
                    keyBoardType = KeyboardType.Number
                )

                PillButton(
                    title = "Post Product",
                    color = ShopKartUtils.black.toInt(),
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .align(Alignment.CenterHorizontally)
                ) {

                    //checking all fields are completed
                    if (selectedProductImageUri.value != null && productTitle.value.isNotEmpty() && productPrice.value.isNotEmpty() && productDescription.value.isNotEmpty() && stock.value.isNotEmpty() && selectedOption.value != "Select Category") {
                        viewModel.uploadProductToStorageGetUrl(
                            selectedImageUri = selectedProductImageUri.value,
                            title = productTitle.value.trim(),
                            price = productPrice.value.trim(),
                            desc = productDescription.value.trim(),
                            category = selectedOption.value,
                            stock = stock.value,
                        ) {
                            selectedProductImageUri.value = null
                            navHostController.popBackStack()
                            productTitle.value = ""
                            productPrice.value = ""
                            productDescription.value = ""
                            stock.value = ""
                            Toast.makeText(context, "Product Uploaded", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}