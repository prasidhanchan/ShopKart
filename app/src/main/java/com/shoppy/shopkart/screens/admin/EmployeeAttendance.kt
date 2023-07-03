package com.shoppy.shopkart.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.models.MAttendance
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EmployeeAttendance(navController: NavHostController,viewModel: AdminScreenViewModel = hiltViewModel()){

    val day = SimpleDateFormat("dd", Locale.ENGLISH)
    val date = Date()
    val today = day.format(date).toString()

    val attendanceList = remember { mutableStateOf(emptyList<MAttendance>()) }

    val context = LocalContext.current

    attendanceList.value = viewModel.fireAttendance.value.data!!.toMutableList()

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Attendance", spacing = 60.dp) }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), shape = RoundedCornerShape(12.dp)) {

                Column(modifier = Modifier
                    .padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

                        Text(text = "DAY : ", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
                        Text(text = today, style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

                Text(text = "Employee List", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), modifier = Modifier.padding(10.dp))
                PillButton(title = "Clear", color = ShopKartUtils.black.toInt(), modifier = Modifier.width(100.dp)){ viewModel.clearAttendance(context = context) }
            }

            LazyColumn {
                items(items = attendanceList.value) { card ->

                    AttendanceItem(card = card, day = today, viewModel = viewModel, navController = navController)

                }
            }
        }
    }
}

@Composable
fun AttendanceItem(card: MAttendance,day: String,navController: NavHostController,viewModel: AdminScreenViewModel){

    val isEnabledP = remember { mutableStateOf(true) }
    val isEnabledAB = remember { mutableStateOf(true) }

    val today = when(day){
        "01" -> card.day01
        "02" -> card.day02
        "03" -> card.day03
        "04" -> card.day04
        "05" -> card.day05
        "06" -> card.day06
        "07" -> card.day07
        "08" -> card.day08
        "09" -> card.day09
        "10" -> card.day10
        "11" -> card.day11
        "12" -> card.day12
        "13" -> card.day13
        "14" -> card.day14
        "15" -> card.day15
        "16" -> card.day16
        "17" -> card.day17
        "18" -> card.day18
        "19" -> card.day19
        "20" -> card.day20
        "21" -> card.day21
        "22" -> card.day22
        "23" -> card.day23
        "24" -> card.day24
        "25" -> card.day25
        "26" -> card.day26
        "27" -> card.day27
        "28" -> card.day28
        "29" -> card.day29
        "30" -> card.day30
        "31" -> card.day31
        else -> card.day01
    }

    //Disabling button according to present or absent
    when(today){
        "Present" -> isEnabledP.value = false
        "Absent" -> isEnabledAB.value = false
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .padding(top = 8.dp, bottom = 8.dp), shape = RoundedCornerShape(15.dp)) {

                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

                    Box(modifier = Modifier
                        .size(60.dp)){
                        Surface(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(15.dp), border = BorderStroke(3.dp,Color.Black)) {

                                Icon(
                                    painter = painterResource(id = R.drawable.profile),
                                    contentDescription = "Profile Image",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                        }
                    }

                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .width(210.dp)
                        .padding(start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

                        Text(text = "Name : ${card.name}", style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(text = "Phone : ${card.phone_no}", style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
                        Text(text = card.email!!, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(text = today.toString(), style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), maxLines = 1, overflow = TextOverflow.Ellipsis)

                    }

                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .width(60.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                        PillButton(title = "P", color = ShopKartUtils.black.toInt(), enabled = isEnabledP.value, modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)){
                            viewModel.presentOrAbsent(PAB = "Present", orderId = card.id!!, Day = day){
                                navController.popBackStack()
                                navController.navigate(BottomNavScreens.EmployeeAttendance.route)
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        PillButton(title = "AB", color = ShopKartUtils.black.toInt(), enabled = isEnabledAB.value, modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)){
                            viewModel.presentOrAbsent(PAB = "Absent", orderId = card.id!!, Day = day){
                                navController.popBackStack()
                                navController.navigate(BottomNavScreens.EmployeeAttendance.route)
                            }
                        }
                    }
                }
            }
        }