package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ProfileCards(modifier: Modifier = Modifier,
                 title: String,
                 leadingIcon: Int,
                 space: Dp = 170.dp,
                 tint: Color = Color.Black,
                 isChecked: MutableState<Boolean> = mutableStateOf(false),
                 showButton: Boolean = false,
                 isButtonEnabled: Boolean = true,
                 onClick: () -> Unit){
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onClick.invoke() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {

                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = title,
                    tint = tint,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, fontFamily = roboto
                    )
                )

                Spacer(modifier = Modifier.width(space))

                if (!showButton) Icon(painter = painterResource(id = R.drawable.arrow_forward), contentDescription = title,tint = Color.Black.copy(alpha = 0.5f))
                if (showButton) Switch(checked = isChecked.value, onCheckedChange = {
                    onClick.invoke()
                    isChecked.value = it }, enabled = isButtonEnabled)
            }
}

//@Preview
//@Composable
//fun Prev(){
//    ProfileCards(title = "Admin", icon = R.drawable.ic_admin) {
//
//    }
//}
