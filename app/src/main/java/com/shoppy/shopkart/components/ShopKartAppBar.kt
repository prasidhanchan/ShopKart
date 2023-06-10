package com.shoppy.shopkart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ShopKartAppBar(userName: String?, profile_url: String?, onClick: () -> Unit = {  }){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(170.dp),
        color = ShopKartUtils.offWhite) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

            Row(modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))){

                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "ShopKart Logo" )
                }
                Spacer(modifier = Modifier.width(15.dp))

                Text(text = "ShopKart", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto))

                Spacer(modifier = Modifier.width(50.dp))

                //Right side Composable only shown if username is not empty
                if (userName != ""){

                    Text(text = "Hello,\n$userName",
                        textAlign = TextAlign.Center, maxLines = 2,
                        overflow = TextOverflow.Ellipsis, modifier = Modifier
                            .width(60.dp)
                            .padding(end = 5.dp), style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

                    Surface(modifier = Modifier.size(45.dp), shape = CircleShape, border = BorderStroke(2.dp,Color.Black)) {

                        if (profile_url != "") AsyncImage(model = profile_url, contentDescription = "Profile Image", placeholder = painterResource(id = R.drawable.dummy_profile), contentScale = ContentScale.Crop)
                        else Image(painter = painterResource(id = R.drawable.dummy_profile), contentDescription = "Profile Image")

                    }
                }else{
                    Box(modifier = Modifier.width(100.dp))
                }
            }
            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .padding(start = 20.dp, end = 20.dp, top = 22.dp)
                .clickable { onClick.invoke() },
                shape = RoundedCornerShape(12.dp),
            ) {

                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

                    Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search", tint = Color.Black.copy(alpha = 0.5f))

                    Text(text = "MacBook Pro", style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto), modifier = Modifier.padding(start = 20.dp), color = Color.Black.copy(alpha = 0.5f))

                }

            }
        }

    }
}

@Composable
fun ShopKartAppBar2(userName: String?, profile_url: String?, navHostController: NavHostController,onClick: () -> Unit = {  }){
    val constraints = ConstraintSet{
        val shopKartIcon = createRefFor(id = "ShopKartIcon")
        val shopKart = createRefFor(id = "ShopKart")
        val box = createRefFor(id = "box")
        val name = createRefFor(id = "UserName")
        val profileImage = createRefFor(id = "ProfileImage")
        val searchBar = createRefFor(id = "SearchBar")

        constrain(shopKartIcon){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(parent.start, margin = 20.dp)
            end.linkTo(shopKart.start)
            bottom.linkTo(searchBar.top)
        }
        
        constrain(shopKart){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(shopKartIcon.end)
            end.linkTo(name.start, margin = 40.dp)
            bottom.linkTo(searchBar.top)
        }
        
        constrain(box){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(shopKart.end)
            end.linkTo(profileImage.start)
            bottom.linkTo(searchBar.top)
        }
        constrain(name){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(shopKart.end)
            end.linkTo(profileImage.start)
            bottom.linkTo(searchBar.top)
        }
        
        constrain(profileImage){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(name.end)
            end.linkTo(parent.end, margin = 20.dp)
            bottom.linkTo(searchBar.top)
        }

        constrain(searchBar){
            top.linkTo(shopKartIcon.top, margin = 40.dp)
            start.linkTo(parent.start, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
            bottom.linkTo(parent.bottom)
        }
    }
    
    ConstraintLayout(constraintSet = constraints, modifier = Modifier
        .height(170.dp)
        .fillMaxWidth()) {

        Box(modifier = Modifier
            .layoutId("ShopKartIcon")
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))){

            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "ShopKart Logo" )
        }

        Text(text = "ShopKart", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto), modifier = Modifier.layoutId("ShopKart"))

        //Right side Composable only shown if username is not empty
        if (userName != ""){

            val user = userName?.split(" ")

            Text(text = "Hello,\n${user?.get(0)}",
                textAlign = TextAlign.Center, maxLines = 2,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .layoutId("UserName")
                    .width(80.dp)
                    .padding(end = 5.dp), style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

            Surface(modifier = Modifier
                .layoutId("ProfileImage")
                .size(45.dp), shape = CircleShape, border = BorderStroke(2.dp,Color.Black)) {

                if (profile_url != "") AsyncImage(model = profile_url, contentDescription = "Profile Image", placeholder = painterResource(id = R.drawable.dummy_profile), contentScale = ContentScale.Crop, modifier = Modifier
                    .clickable { navHostController.navigate(BottomNavScreens.MyProfile.route) })
                else Image(painter = painterResource(id = R.drawable.dummy_profile), contentDescription = "Profile Image", modifier = Modifier
                    .clickable { navHostController.navigate(BottomNavScreens.MyProfile.route) })

            }
        }else{
            Box(modifier = Modifier
                .width(100.dp)
                .layoutId("box"))
        }

        Surface(modifier = Modifier
            .layoutId("SearchBar")
            .fillMaxWidth()
            .height(78.dp)
            .padding(start = 20.dp, end = 20.dp, top = 22.dp)
            .clickable { onClick.invoke() },
            shape = RoundedCornerShape(12.dp),
        ) {

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search", tint = Color.Black.copy(alpha = 0.5f))

                Text(text = "MacBook Pro", style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto), modifier = Modifier.padding(start = 20.dp), color = Color.Black.copy(alpha = 0.5f))

            }

        }
    }
}



@Preview
@Composable
fun Prev(){
    ShopKartAppBar2("Prasidh","", navHostController = rememberNavController())
}