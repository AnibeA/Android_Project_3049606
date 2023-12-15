package com.griffith.a3049606

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
        // Define a green color for the buttons
        val buttonColor = Color(120, 200, 150) // Example green color

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(101, 115, 126)
        ) {
            // Use Column instead of Box for vertical arrangement
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center the buttons horizontally
                verticalArrangement = Arrangement.Center, // Center the buttons vertically
                modifier = Modifier.fillMaxSize()
            ) {
                // First button to navigate to the DecksActivity
                Button(
                    onClick = { navigateToDecksActivity() },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors( buttonColor) // Set button color
                ) {

                    Text(
                        text = "View Decks",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Additional button to navigate to the AddDeckActivity
                Button(
                    onClick = { navigateToAddDeckActivity() }, // Add the corresponding function
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(buttonColor) // Set button color
                ) {

                    Text(
                        text = "Add deck",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )                    // ...
                }
            }
        }
    }

    // Function to navigate to AddDeckActivity
    private fun navigateToAddDeckActivity() {
        val intent = Intent(this, AddDeck::class.java) // Intent for AddDeckActivity
        startActivity(intent)
    }
    private fun navigateToDecksActivity() {
        val intent = Intent(this, DecksActivity::class.java) // Create an intent for DecksActivity
        startActivity(intent) // Start the activity
    }
}
