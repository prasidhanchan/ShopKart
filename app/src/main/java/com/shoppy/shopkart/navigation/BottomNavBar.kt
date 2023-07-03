package com.shoppy.shopkart.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.ShopKartUtils

@Composable
fun BottomNavBar(navHostController: NavHostController,
                 onItemSelected:(BottomNavScreens) -> Unit) {


    //Retrieving Email from Firebase Auth
    val email = remember { mutableStateOf(FirebaseAuth.getInstance().currentUser?.email.toString()) }

    //If Admin or Employee Account is logged in change BottomNav Bar padding to 80.dp else 40.dp
    val padding = if (email.value.contains("admin.") || email.value.contains("employee.")) 80.dp else 40.dp

    //If Admin or Employee Account is logged in show different Bottom Bar
    val items = if (email.value.contains("admin.") || email.value.contains("employee.")) BottomNavScreens.ItemsAdmin.list else BottomNavScreens.Items.list

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val currentScreen = navBackStackEntry?.destination

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .padding(start = padding, end = padding, top = 35.dp, bottom = 10.dp),
        shape = RoundedCornerShape(40.dp),
        color = ShopKartUtils.darkBlue,
    ) {

        Row(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                //Checking which screen is selected
                val isSelected = currentScreen?.hierarchy?.any { it.route == item.route } == true

                BottomNavBarItems(item = item, isSelected = isSelected) {
                    onItemSelected(item)
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id){saveState = true}

                        //Avoid multiple copies of same destination when selecting again
                        launchSingleTop = true

                        //Restore state when re selecting a previously selected item
//                        restoreState = true
                    }
                }

            }
        }
    }
}

    @Composable
    fun BottomNavBarItems(
        item: BottomNavScreens,
        isSelected: Boolean,
        onClick: () -> Unit = {}
    ) {

        val contentColor = if (isSelected) Color.White else Color.Gray

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onClick)
                    .width(55.dp)
            ) {

                Column(
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = item.icon!!), contentDescription = item.title,
                        tint = contentColor)
                }
            }

//           AnimatedVisibility(visible = isSelected) {
//
//                    Text(text = item.title,
//                        color = contentColor,
//                    modifier = Modifier.padding(bottom = 10.dp),
//                    style = TextStyle(fontWeight = FontWeight.Bold)
//                    )
//            }
        }
    }