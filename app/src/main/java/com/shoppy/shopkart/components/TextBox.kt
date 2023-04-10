package com.shoppy.shopkart.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextBox(modifier:Modifier = Modifier,
            title: String = "",
            labelId:String,
            onChange:MutableState<String>,
            isSingleLine:Boolean = true,
            leadingIcon: ImageVector,
            keyBoardType: KeyboardType = KeyboardType.Text
){
 TextField(value = title,
            onValueChange = {
                onChange.value = it },
            label = { Text(text = labelId)},
            singleLine = isSingleLine,
            leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = title) },
            modifier = modifier.padding(10.dp)
                .width(340.dp),
            colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xFFE0ECEA)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
            shape = RoundedCornerShape(10.dp),
            enabled = true

        )
}