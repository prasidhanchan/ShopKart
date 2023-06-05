package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun PasswordTextBox(value: String,onChange: MutableState<String>,imeAction: ImeAction = ImeAction.Next,modifier: Modifier = Modifier){

    val isClicked = remember { mutableStateOf(false) }

    val trailingIcon = if (isClicked.value) R.drawable.visibility_off else R.drawable.visibility_on

    TextField(value = value,
        onValueChange = { onChange.value = it },
        label = { Text(text = "Password", style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto)) },
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.lock), contentDescription = value, modifier = modifier.size(25.dp)) },
        trailingIcon = { Icon(painter = painterResource(id = trailingIcon), contentDescription = value, modifier = modifier.size(25.dp).clickable { isClicked.value = !isClicked.value}) },
        modifier = modifier
//            .padding(10.dp)
            .width(340.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xFFE0ECEA)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeAction),
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if (isClicked.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}