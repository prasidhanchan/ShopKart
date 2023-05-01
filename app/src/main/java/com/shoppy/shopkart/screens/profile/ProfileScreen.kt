package com.shoppy.shopkart.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun ProfileScreen(navController: NavController,
                  email: String,
                  admin: () -> Unit,
                  about: () -> Unit,
                  myProfile: () -> Unit,
                  signOut: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ShopKartUtils.offWhite
    ) {

        Column(modifier = Modifier
            .padding(top = 30.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val checkAdmin = email.contains("admin.")

            if (checkAdmin) {

                ProfileCards(title = "Admin", icon = R.drawable.ic_admin, tint = Color(0xFFFF5722)) {
                    admin()
                }
            } else {
                Box {}
            }

            ProfileCards(title = "My Profile", icon = R.drawable.ic_profile,tint = Color(0xFFBFCF1A)) {
                myProfile()
            }

            ProfileCards(title = "Log Out", icon = R.drawable.ic_logout,tint = Color.Red.copy(0.5f)) {
//            Log.d("SIGNOUT", "ProfileScreen: ${signOut.invoke()}")
                signOut()
            }

            ProfileCards(title = "About", icon = R.drawable.ic_info,tint = Color.Blue.copy(0.5f)) {
                about()
            }
        }
    }
}