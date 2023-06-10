package com.shoppy.shopkart.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun AdminScreen(navController: NavController) {

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Admin") }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
                shape = RoundedCornerShape(14.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfileRowComp(
                        title = "Add/Remove Brand"
                    ) { navController.navigate(BottomNavScreens.AddRemoveBrandAdmin.route) }

                    Divider()

                    ProfileRowComp(
                        title = "Add Employee"
                    ) { navController.navigate(BottomNavScreens.AddEmployee.route) }

                    Divider()

                    ProfileRowComp(
                        title = "Add Product/Slider",
                    ) { navController.navigate(BottomNavScreens.AddProductSliderAdmin.route) }

                    Divider()

                    ProfileRowComp(
                        title = " Employee Attendance",
                    ) { navController.navigate(BottomNavScreens.EmployeeAttendance.route) }
                }
            }

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .padding(top = 10.dp),
                        shape = RoundedCornerShape(14.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfileRowComp(
                        title = "Ordered Items",
                    ) { navController.navigate(BottomNavScreens.OrderedItems.route) }

                    Divider()

                    ProfileRowComp(
                        title = "On The Way Items",
                    ) { navController.navigate(BottomNavScreens.OnTheWayItems.route) }

                    Divider()

                    ProfileRowComp(
                        title = "Delivered Items",
                    ) { navController.navigate(BottomNavScreens.DeliveredItems.route) }
                }
            }
        }
    }
}

@Composable
fun ProfileRowComp(leadingIcon: Int? = null, title: String,onClick:() -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 8.dp)
        .clickable { onClick.invoke() }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        leadingIcon?.let { painterResource(id = it) }
            ?.let { Icon(painter = it, contentDescription = title) }
        Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
//        Spacer(modifier = Modifier.width(115.dp))
        Icon(painter = painterResource(id = R.drawable.arrow_forward), contentDescription = "Arrow Forward", tint = Color.Black.copy(alpha = 0.5f))
    }
}



//@Preview
//@Composable
//fun Prev(){
//    EmployeeScreen(navController = rememberNavController())
//}