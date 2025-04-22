package com.shoppy.shopkart.screens.admin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.GalleryLaunchComp
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.SelectedImageItem
import com.shoppy.shopkart.components.TextBox2

@Composable
fun AddRemoveBrandAdmin(
    navHostController: NavHostController,
    viewModel: AdminScreenViewModel = hiltViewModel()
) {

    val selectedBrandImageUri = remember { mutableStateOf<Uri?>(null) }

    val launchGalleryBrand =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedBrandImageUri.value = uri })

    val brandName = remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BackButton(
                navController = navHostController,
                topBarTitle = "Add/Remove Brand",
                spacing = 30.dp
            )
        },
        backgroundColor = ShopKartUtils.offWhite
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            //Add Brand
            GalleryLaunchComp(
                title = "Select Logo",
                modifier = Modifier.size(200.dp),
                color = Color.Black.copy(alpha = 0.1f)
            ) {
                launchGalleryBrand.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            if (selectedBrandImageUri.value != null) SelectedImageItem(uris = selectedBrandImageUri.value)

            TextBox2(value = brandName.value, onChange = brandName, placeHolder = "Brand Name")

            PillButton(
                title = "Add Brand",
                color = ShopKartUtils.black.toInt(),
                modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
            ) {
                if (selectedBrandImageUri.value == null) {
                    Toast.makeText(context, "Select a LOGO", Toast.LENGTH_SHORT).show()
                } else if (brandName.value == "") {
                    Toast.makeText(context, "Add Brand Name", Toast.LENGTH_SHORT).show()
                } else {
                    navHostController.popBackStack()
                    viewModel.uploadBrand(
                        selectedImageUri = selectedBrandImageUri.value,
                        brandName = brandName.value.trim()
                    ) { selectedBrandImageUri.value = null }
                    Toast.makeText(context, "Brand Added", Toast.LENGTH_SHORT).show()
                }

            }

            Divider(modifier = Modifier.padding(bottom = 10.dp))

            //Remove brand
            TextBox2(value = brandName.value, onChange = brandName, placeHolder = "Brand Name")

            PillButton(title = "Remove Brand", color = ShopKartUtils.black.toInt()) {
                if (brandName.value.isNotEmpty()) {
                    viewModel.removeBrand(brandName = brandName.value)
                    navHostController.popBackStack()
                    Toast.makeText(context, "Brand ${brandName.value} removed", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Brand Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}