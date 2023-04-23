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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(currentScreen: String,navHostController: NavHostController,
                 onItemSelected:(BottomNavScreens) -> Unit) {

    val items = BottomNavScreens.Items.list

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(start = 25.dp, end = 25.dp, top = 35.dp, bottom = 10.dp),
        shape = RoundedCornerShape(40.dp),
        color = Color.Black,
    ) {

        Row(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                BottomNavBarItems(item = item, isSelected = item.route == currentScreen) {
                    onItemSelected(item)
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                    }
                }

            }
        }
    }

}

    @Composable
    fun BottomNavBarItems(
        item: BottomNavScreens, isSelected: Boolean,
        onClick: () -> Unit = {}
    ) {

        val background = if (isSelected) Color.Transparent else Color.Transparent
        val contentColor = if (isSelected) Color.White else Color.Gray

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(background)
                    .clickable(onClick = onClick)
                    .width(55.dp)
            ) {

                Column(
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = item.icon), contentDescription = item.title,
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