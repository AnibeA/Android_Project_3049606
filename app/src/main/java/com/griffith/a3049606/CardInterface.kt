package com.griffith.a3049606

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Constants for use in gyroscope-based interactions
const val DELTA_TIME: Float = 0.01f // Time interval for updates (10 milliseconds)
const val THRESHOLD = 1.0f // Threshold for gyroscope sensitivity

// Main activity class for card interface
class CardInterface : ComponentActivity() {
    // Variables for sensor management
    private lateinit var sensorManager: SensorManager
    private var gyroscopeListener: SensorEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Sets the UI content using Jetpack Compose
        setContent {
            val isFlippedState = remember { mutableStateOf(false) } // State to track card flip

            FlashcardItem(isFlippedState) // Display the flashcard item

            // Setting up the gyroscope sensor
            val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            gyroscopeListener = GyroscopeListener(isFlippedState)
            sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the sensor listener when the activity is destroyed
        gyroscopeListener?.let { sensorManager.unregisterListener(it) }
    }

    // Inner class to handle gyroscope events for flipping the card
    private inner class GyroscopeListener(private val isFlippedState: MutableState<Boolean>) : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
                val rotationRateY = event.values[1]
                if (Math.abs(rotationRateY) > THRESHOLD) {
                    // If the rotation rate crosses the threshold, flip the card
                    runOnUiThread {
                        isFlippedState.value = !isFlippedState.value
                    }
                }
            }
        }
    }

    // Composable function for displaying the flashcard item
    @Composable
    fun FlashcardItem(isFlippedState: MutableState<Boolean>) {
        // Surface layout for the card
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(101, 115, 126) // Background color
        ) {
            // Column layout for centering the card
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 50.dp) // Padding at the bottom
            ) {
                // Composable function for the flip card
                FlipCard(
                    isFlippedState = isFlippedState,
                    cardFace = CardFace(
                        frontText = "Front of card text will be here",
                        backText = "Back of card text will be here"
                    ),
                    modifier = Modifier
                        .height(250.dp)
                        .width(350.dp)
                        .padding(bottom = 20.dp) // Padding between card and button
                )
                AddCardButton() // Button to add a new card
            }
        }
    }

    // Composable function for the flip card
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FlipCard(
        isFlippedState: MutableState<Boolean>,
        cardFace: CardFace,
        modifier: Modifier = Modifier
    ) {
        // Animation for the flipping effect
        val rotation = animateFloatAsState(
            targetValue = if (isFlippedState.value) 180f else 0f, // Target rotation based on state
            animationSpec = tween(durationMillis = 400) // Animation duration
        )

        // Card UI with flip effect
        Card(
            onClick = { isFlippedState.value = !isFlippedState.value }, // Flip the card on click
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation.value // Apply Y-axis rotation
                    cameraDistance = 12f * density // Set camera distance for 3D effect
                }
        ) {
            // Display the appropriate side of the card based on rotation
            if (rotation.value <= 90f) {
                // Front side of the card
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = cardFace.frontText, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                }
            } else {
                // Back side of the card
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }, // Complete the rotation for the back side
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = cardFace.backText, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                }
            }
        }
    }

    // Composable function for the Add Card button
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddCardButton() {
        val context = LocalContext.current
        FloatingActionButton(
            containerColor = Color(120, 200, 150), // Button color
            contentColor = Color.White, // Icon color
            onClick = {
                // Intent to navigate to AddDeck activity
                val intent = Intent(context, AddDeck::class.java)
                context.startActivity(intent)
            }
        ) {
            Icon(Icons.Filled.Add, "Add new card") // Icon for the button
        }
    }

    // Data class to represent the front and back text of the card
    data class CardFace(val frontText: String, val backText: String)

    // Composable function for previewing the flashcard item in Android Studio
    @Preview
    @Composable
    fun PreviewFlashcardItem() {
        FlashcardItem(remember { mutableStateOf(false) }) // Preview with a default unflipped state
    }
}
