package com.griffith.a3049606

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// MainActivity - the main entry point of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the UI content of this activity to MainScreen composable function
        setContent {
            MainScreen()
        }
    }

    // Preview annotation for rendering the composable in the design preview
    @Preview
    @Composable
    fun MainScreen() {
        // Surface layout representing the main screen of the app
        Surface(
            modifier = Modifier.fillMaxSize(), // Fills the entire screen
            color = Color(101, 115, 126) // Background color
        ) {
            // Box layout for centering the button
            Box(
                contentAlignment = Alignment.Center // Aligns the child to the center
            ) {
                // Button to navigate to the DecksActivity
                Button(
                    onClick = { navigateToDecksActivity() }, // Set the onClick behavior
                    shape = RoundedCornerShape(50), // Circular shape for the button
                    modifier = Modifier.padding(16.dp) // Padding around the button
                ) {
                    // Icon inside the button
                    Icon(
                        imageVector = Icons.Filled.PlayArrow, // Play arrow icon
                        contentDescription = "Go to Decks", // Accessibility description
                        tint = Color.White // Icon color
                    )
                    // Text inside the button
                    Text(
                        text = "View Decks", // Button text
                        color = Color.White, // Text color
                        modifier = Modifier.padding(start = 8.dp) // Padding to the start of the text
                    )
                }
            }
        }
    }

    // Function to navigate to DecksActivity
    private fun navigateToDecksActivity() {
        val intent = Intent(this, DecksActivity::class.java) // Create an intent for DecksActivity
        startActivity(intent) // Start the activity
    }
}
