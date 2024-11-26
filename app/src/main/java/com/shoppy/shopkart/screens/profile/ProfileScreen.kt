package com.shoppy.shopkart.screens.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.ProfileCards
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.myorderdetails.ShopKartDialog

//252.dp
@Composable
fun ProfileScreen(navController: NavController,
                  email: String,
//                  admin: () -> Unit,
//                  about: () -> Unit,
//                  myProfile: () -> Unit,
                  signOut: () -> Unit) {


//    @Composable
//    fun Notification(){

        val context = LocalContext.current

        val hasNotificationPermission = remember {
            //Checking if Android 13+ or not if not assigning true directly
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                mutableStateOf(
                    ContextCompat.checkSelfPermission(context,
                        Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
            }else{
                mutableStateOf(true)
            }
        }

        val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
            if (isGranted){
                hasNotificationPermission.value = true
            }else{
//                hasNotificationPermission.value = false
                Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
            }
        })

    val isButtonEnabled = remember {
        mutableStateOf(true)
    }

    if (hasNotificationPermission.value) isButtonEnabled.value = false

//        if (!hasNotificationPermission.value){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionLauncher.launch(
//                Manifest.permission.POST_NOTIFICATIONS)
//        }
//    }


    val checkAdmin = email.contains("admin.")

    val checkEmployee = email.contains("employee.")

    val surfaceHeight = if (checkAdmin) 252.dp else if (checkEmployee) 192.dp else 250.dp

    val openDialog = remember { mutableStateOf(false) }

    Scaffold(backgroundColor = ShopKartUtils.offWhite) { innerPadding ->


//    Surface(
//        modifier = Modifier
//            .fillMaxSize(),
//        color = ShopKartUtils.offWhite
//    ) {
//
//        Column(modifier = Modifier
//            .padding(top = 30.dp, start = 12.dp, end = 12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
//        ) {
//
//
//        }
//    }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 50.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(surfaceHeight)
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (checkAdmin) {

                        ProfileCards(
                            title = "Admin",
                            leadingIcon = R.drawable.ic_admin,
                            tint = Color(0xFFFF5722),
                            space = 200.dp
                        ) {
                            navController.navigate(BottomNavScreens.AdminScreen.route)
                        }
                        Divider()
                    } else {
                        Box {}
                    }

                    //Show employee option for both admin and employee
                    if (checkEmployee) {

                        ProfileCards(
                            title = "Employee",
                            leadingIcon = R.drawable.ic_admin,
                            tint = Color(0xFFFF22E2),
                            space = 175.dp
                        ) {
                            navController.navigate(BottomNavScreens.EmployeeScreen.route)
                        }
                        Divider()
                    } else {
                        Box {}
                    }

                    if (!checkEmployee){

                        ProfileCards(
                            title = "My Profile",
                            leadingIcon = R.drawable.ic_profile,
                            tint = Color(0xFFBFCF1A)
                        ) {
                            navController.navigate(BottomNavScreens.MyProfile.route)
                        }
                        Divider()
                    }else{
                        Box{}
                    }

                    ProfileCards(
                        title = "Log Out",
                        leadingIcon = R.drawable.ic_logout,
                        tint = Color.Red.copy(0.5f),
                        space = 190.dp
                    ) {
                        openDialog.value = true
                    }

                    Divider()

                    ProfileCards(
                        title = "About",
                        leadingIcon = R.drawable.ic_info,
                        tint = Color.Blue.copy(0.5f),
                        space = 205.dp
                    ) {
                        navController.navigate(BottomNavScreens.About.route)
                    }

                    Divider()

                    ProfileCards(
                        title = "Notification",
                        leadingIcon = R.drawable.notification,
                        tint = Color(0xFFD5EC08),
                        space = 122.dp,
                        isChecked = hasNotificationPermission,
                        showButton = true,
                        isButtonEnabled = isButtonEnabled.value
                    ) {
//                        hasNotificationPermission.value = !hasNotificationPermission.value
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission.value) permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                        navController.navigate(BottomNavScreens.About.route)
                    }
                }

            }
        }
    }

    //Calling Alert Dialog
    ShopKartDialog(openDialog = openDialog,
        onTap = { signOut.invoke() },
        context = context,
        navController = navController,
        title = "Log Out",
        subTitle = "Are You Sure, you want to Log Out?",
        button1 = "Log Out",
        button2 = "Cancel",
        toast = "Logged Out")

}