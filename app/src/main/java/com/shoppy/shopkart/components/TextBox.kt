package com.shoppy.shopkart.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun TextBox(
    modifier:Modifier = Modifier,
    value: String,
    labelId:String,
    onChange:MutableState<String>,
    isSingleLine:Boolean = true,
    leadingIcon: Int? = null,
    keyBoardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTrans: VisualTransformation = VisualTransformation.None,
){
    TextField(value = value,
     onValueChange = { onChange.value = it },
     label = { Text(text = labelId, style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto)) },
     singleLine = isSingleLine,
     leadingIcon = {
         if (leadingIcon != null) { Icon(painter = painterResource(id = leadingIcon), contentDescription = value, modifier = modifier.size(25.dp)) }
                   },
     modifier = modifier
         .padding(10.dp)
         .width(340.dp),
     colors = TextFieldDefaults.textFieldColors(
         focusedIndicatorColor = Color.Transparent,
         unfocusedIndicatorColor = Color.Transparent,
         backgroundColor = Color(0xFFE0ECEA)),
     keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
     shape = RoundedCornerShape(10.dp),
     enabled = true,
     visualTransformation = visualTrans)
}

//@Composable
//fun TextBoxInt(title: String,onChange: MutableState<String>){
//    TextField(value = title, onValueChange = {
//        onChange.value = it.isD })
//}