package com.shoppy.shopkart.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.components.ProfileCards
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens

@Composable
fun ProfileScreen(navController: NavController,
                  email: String,
                  admin: () -> Unit,
                  about: () -> Unit,
                  signOut: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        Column(modifier = Modifier
            .padding(top = 50.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val checkAdmin = email.contains("admin.")

            if (checkAdmin) {

                ProfileCards(title = "Admin", icon = R.drawable.ic_admin, tint = Color.Magenta.copy(0.5f)) {
                    admin()
                }
            } else {
                Box {}
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