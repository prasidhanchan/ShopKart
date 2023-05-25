package com.shoppy.shopkart.screens.admin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun AddEmployee(navHostController: NavHostController,viewModel: AdminScreenViewModel = hiltViewModel()){

    val employeeName = remember { mutableStateOf("") }
    val employeeEmail = remember { mutableStateOf("employee.") }
    val employeePass = remember { mutableStateOf("") }
    val employeeAddress = remember { mutableStateOf("") }
    val employeePhone = remember { mutableStateOf("") }

    val errorState = remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { BackButton(navController = navHostController, topBarTitle = "Add Employee", spacing = 50.dp) }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            TextBox(value = employeeName.value, labelId = "Employee name", onChange = employeeName, leadingIcon = R.drawable.ic_profile)
            TextBox(value = employeeEmail.value, labelId = "Employee email", onChange = employeeEmail, leadingIcon = R.drawable.email)
            TextBox(value = employeePass.value, labelId = "Employee password", onChange = employeePass, leadingIcon = R.drawable.lock)
            TextBox(value = employeeAddress.value, labelId = "Employee address", onChange = employeeAddress, leadingIcon = R.drawable.address)
            TextBox(value = employeePhone.value, labelId = "Employee phone", onChange = employeePhone, leadingIcon = R.drawable.call)

            PillButton(title = "Add Employee", color = ShopKartUtils.black.toInt(), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)){
                if (employeeName.value.isNotEmpty() &&
                    employeeEmail.value.isNotEmpty() &&
                    employeePass.value.isNotEmpty() &&
                    employeeAddress.value.isNotEmpty()
                    && employeePhone.value.isNotEmpty()){

                    viewModel.addEmployee(employee_name = employeeName.value.trim(),
                        employee_email = employeeEmail.value.trim(),
                        employee_password = employeePass.value.trim(),
                        employee_address = employeeAddress.value.trim(),
                        employee_phone = employeePhone.value.trim(),
                        success = {
                            navHostController.popBackStack()
                            Toast.makeText(context,"Employee Account Created",Toast.LENGTH_SHORT).show() }){ error ->
                        errorState.value = error
                    }
                }else{
                    errorState.value = "Fields cannot be empty"
                }
            }

            Text(text = errorState.value, style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))

        }
    }
}