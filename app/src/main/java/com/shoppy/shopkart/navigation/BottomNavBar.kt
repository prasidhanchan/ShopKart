package com.shoppy.shopkart.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(currentScreen: String,navHostController: NavHostController,
                 onItemSelected:(BottomNavScreens) -> Unit) {

    val items = BottomNavScreens.Items.list

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        shape = RoundedCornerShape(35.dp),
        color = Color.Gray.copy(alpha = 0.5f),
    ) {

        Row(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                BottomNavBarItems(item = item, isSelected = item.route == currentScreen,) {
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
        val contentColor = if (isSelected) Color.Black else MaterialTheme.colors.onBackground

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
                    modifier = Modifier.padding(top = 15.dp, bottom = 8.dp, start = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = item.icon), contentDescription = item.title,
                        tint = contentColor)
                }
            }

           AnimatedVisibility(visible = isSelected) {

                    Text(text = item.title,
                        color = contentColor,
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                    )
            }
        }
    }