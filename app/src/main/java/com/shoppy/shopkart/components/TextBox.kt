package com.shoppy.shopkart.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextBox(
    modifier:Modifier = Modifier,
    title: String = "",
    labelId:String,
    onChange:MutableState<String>,
    isSingleLine:Boolean = true,
    leadingIcon: ImageVector? = null,
    keyBoardType: KeyboardType = KeyboardType.Text,
    visualTrans: VisualTransformation = VisualTransformation.None
){
 TextField(value = title,
            onValueChange = {
                onChange.value = it },
            label = { Text(text = labelId)},
            singleLine = isSingleLine,
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription = title)
                }
            },
            modifier = modifier.padding(10.dp)
                .width(340.dp),
            colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xFFE0ECEA)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
            shape = RoundedCornerShape(10.dp),
            enabled = true,
            visualTransformation = visualTrans

        )
}