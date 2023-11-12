package com.griffith.a3049606

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class CardInterface : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardItem()
            }
        }
    }

@Preview
@Composable
fun FlashcardItem(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(150, 216, 250)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Surface(
                modifier = Modifier
                    .height(250.dp)
                    .width(350.dp)
                    .padding(10.dp),
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            ){
                Text("Front of card text will be here ")
            }
        }

    }
}