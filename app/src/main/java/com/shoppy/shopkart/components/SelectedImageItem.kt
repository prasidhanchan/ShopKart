package com.shoppy.shopkart.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun SelectedImageItem(modifier: Modifier = Modifier,
                       uris: Uri?){
    Card(modifier = modifier
        .size(100.dp)
        .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp) {
        Image(painter = rememberAsyncImagePainter(model = uris),
            contentDescription = "Selected Image",
            contentScale = ContentScale.Crop)
    }
}