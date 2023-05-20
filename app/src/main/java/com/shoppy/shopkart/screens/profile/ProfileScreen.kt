package com.shoppy.shopkart.screens.profile

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.ProfileCards
import com.shoppy.shopkart.navigation.BottomNavScreens
//252.dp
@Composable
fun ProfileScreen(navController: NavController,
                  email: String,
//                  admin: () -> Unit,
                  about: () -> Unit,
                  myProfile: () -> Unit,
                  signOut: () -> Unit) {

    val checkAdmin = email.contains("admin.")

    val checkEmployee = email.contains("employee.")

    val surfaceHeight = if (checkAdmin) 252.dp else if (checkEmployee) 252.dp else 190.dp

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

                    ProfileCards(
                        title = "My Profile",
                        leadingIcon = R.drawable.ic_profile,
                        tint = Color(0xFFBFCF1A)
                    ) {
                        myProfile()
                    }

                    Divider()

                    ProfileCards(
                        title = "Log Out",
                        leadingIcon = R.drawable.ic_logout,
                        tint = Color.Red.copy(0.5f),
                        space = 190.dp
                    ) {
//            Log.d("SIGNOUT", "ProfileScreen: ${signOut.invoke()}")
                        signOut()
                    }

                    Divider()

                    ProfileCards(
                        title = "About",
                        leadingIcon = R.drawable.ic_info,
                        tint = Color.Blue.copy(0.5f),
                        space = 205.dp
                    ) {
                        about()
                    }
                }

            }
        }
    }
}