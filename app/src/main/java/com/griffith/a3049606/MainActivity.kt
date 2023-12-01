package com.griffith.a3049606

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen() // Set the content to the HomeScreen composable function
        }
    }
}

// Preview function for the HomeScreen composable
@Preview
@Composable
fun HomeScreen() {
    val context = LocalContext.current // Retrieve the current context

    Surface(
        Modifier.fillMaxSize(), // Set the Surface to fill the entire available size
        color = Color(101, 115, 126) // Set the background color
    ) {
        Column(
            verticalArrangement = Arrangement.Top, // Align children vertically at the top
            horizontalAlignment = Alignment.Start // Align children horizontally at the start
        ) {
            // DynamicItemList() // Commented out for the purpose of this demonstration
            // A LazyColumn containing clickable items
            LazyColumn {
                item {
                    // Item representing "Deck 1" with click functionality to navigate to CardInterface
                    ClickableItem("Deck 1") {
                        val intent = Intent(context, CardInterface::class.java)
                        context.startActivity(intent)
                    }
                }
                item {
                    // Item representing "Deck 2" with click functionality to navigate to CardInterface
                    ClickableItem("Deck 2") {
                        val intent = Intent(context, CardInterface::class.java)
                        context.startActivity(intent)
                    }
                }
                // Add more items as needed
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom, // Align the row's children vertically at the bottom
            horizontalArrangement = Arrangement.End // Align the row's children horizontally at the end
        ) {
            Surface(
                Modifier.padding(25.dp), // Apply padding to the Surface
                color = Color(101, 115, 126), // Set the background color
                shape = RoundedCornerShape(10.dp) // Apply rounded corners to the Surface
            ) {
                FloatingActionButton(
                    containerColor = Color(120, 200, 150), // Set container color for the FloatingActionButton
                    contentColor = Color.White, // Set content color for the FloatingActionButton
                    onClick = {
                        val intent = Intent(context, AddDeck::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Icon(Icons.Filled.Add, "New Deck") // Display an icon on the FloatingActionButton
                }
            }
        }
    }
}

// Composable function for a clickable item
@Composable
fun ClickableItem(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth() // Set the width to fill the maximum available width
            .padding(20.dp) // Apply padding to the Surface
            .clickable { onClick() }, // Make the item clickable and trigger the provided onClick lambda
        color = Color.White // Set the background color for the item
    ) {
        Text(
            text = text, // Display the provided text
            color = Color.Black, // Set the text color
            modifier = Modifier.padding(16.dp) // Apply padding to the text
        )
    }
}
