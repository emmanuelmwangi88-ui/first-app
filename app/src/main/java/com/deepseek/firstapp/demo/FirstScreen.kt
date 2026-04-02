package com.deepseek.firstapp.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily.Companion.Serif
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepseek.firstapp.R


@Composable
fun FirstScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding()
            .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text(
                text = "welcome to my app",
                fontSize = 24.sp,
                color = Color.Blue,
                fontFamily = Serif,
                fontStyle = FontStyle.Italic,
            )

Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id=R.drawable.yuta),
            contentDescription = "company logo",
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth()
                .clip(CircleShape)

        )

    //row
    Row(
        modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly

    ) {
        //two buttons
        Button(onClick = {}) {
            Text("LOGIN")
        }
        Button(onClick = {}) {
            Text("REGISTER")
        }
    }
        }

}
