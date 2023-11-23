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
            // Set the content of the activity to a FlashcardItem
            FlashcardItem()
        }
    }
}

// Function to preview the FlashcardItem
@Preview
@Composable
fun FlashcardItem() {
    Surface(
        modifier = Modifier.fillMaxSize(), // Set the Surface to fill the entire available size
        color = Color(150, 216, 250) // Set the background color of the Surface
    ) {
        Column(
            verticalArrangement = Arrangement.Center, // Align children vertically at the center
            horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally at the center
        ) {
            Surface(
                modifier = Modifier
                    .height(250.dp) // Set the height of the Surface
                    .width(350.dp) // Set the width of the Surface
                    .padding(10.dp), // Apply padding around the Surface
                color = Color.White, // Set the background color of this Surface
                shape = RoundedCornerShape(20.dp) // Apply rounded corners to the Surface
            ) {
                Text("Front of card text will be here ") // Display text on the Surface
            }
        }
    }
}
