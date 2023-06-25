package com.shoppy.shopkart.components

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shoppy.shopkart.ui.theme.roboto

//Without Leading Icon with Label
@Composable
fun TextBox3(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onChange: MutableState<String>,
    keyBoardType: KeyboardType = KeyboardType.Text,
    trailingIcon: ImageVector? = null,
    placeHolder: String = "",
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    visualTrans: VisualTransformation = VisualTransformation.None,
) {
    TextField(
        value = value, label = { Text(text = label) },
        onValueChange = { onChange.value = it },
        modifier = modifier
            .padding(10.dp)
            .width(340.dp), trailingIcon = {
            if (trailingIcon != null) {
                Icon(imageVector = trailingIcon, contentDescription = value)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xFFE0ECEA)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        shape = RoundedCornerShape(10.dp),
        enabled = true,
        singleLine = isSingleLine,
        maxLines = maxLines,
        placeholder = { Text(text = placeHolder, color = Color.Black.copy(alpha = 0.4f), style = TextStyle(fontFamily = roboto)) },
        visualTransformation = visualTrans)
}