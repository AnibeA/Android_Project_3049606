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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService

const val DELTA_TIME: Float = 0.1f // Define DELTA_TIME as a constant with a value of 0.01 seconds (10 milliseconds)

val threshold = 5.0f // Declare threshold as a constant

var isFlipped by mutableStateOf(false) // Track the flipped state globally
var rotationAngle: Float = 0.0f // Initialize rotation angle

var previousRotationAngle: Float = 0.0f // Initialize previous rotation angle

fun setIsFlipped(rotationRate: Float) {
    // Calculate delta angle
    val deltaAngle = rotationRate * DELTA_TIME

    // Update the rotation angle
    rotationAngle += deltaAngle

    // Update the flipped state based on the rate of change of the rotation angle
    if (deltaAngle > threshold) {
        isFlipped = true // Update isFlipped state to true if the delta angle exceeds the threshold
    } else if (deltaAngle < -threshold) {
        isFlipped = false // Update isFlipped state to false if the delta angle is less than the negative threshold
    }
}


class CardInterface : ComponentActivity() {
    private val gyroscopeListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not used in this example
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
                // Process gyroscope sensor changes here
                val rotationRate = event.values[1] // Get rotation rate in radians per second

                // Integrate rotation rate to calculate rotation angle
                rotationAngle += rotationRate * DELTA_TIME // DELTA_TIME should be the time interval between sensor readings

                // Update card flipped state based on rotation angle
                setIsFlipped(rotationAngle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Set up the sensor manager to access device sensors
            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


            val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            //Register a listener to listen for gyroscope sensor changes with normal delay
            sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)

            // Set the content of the activity to a FlashcardItem
            FlashcardItem()
        }
    }



            data class CardFace(
            val frontText: String,
            val backText: String
        )
        var flashcardData: CardFace? = null // Initialize flashcardData as null

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FlipCard(
        cardFace: CardFace, // Represents the content for the card's front and back
        modifier: Modifier = Modifier, // Modifier to customize the FlipCard layout
        back: @Composable () -> Unit = {}, // Composable function for the back of the card
        front: @Composable () -> Unit = {}, // Composable function for the front of the card
    ) {
        // Track the flipped state of the card
        val (isFlipped, setIsFlipped) = remember { mutableStateOf(false) }

        // Animate the rotation of the card based on the flipped state
        val rotation = animateFloatAsState(
            targetValue = if (isFlipped) 180f else 0f, // Set rotation based on flipped state
            animationSpec = tween(
                durationMillis = 400, // Duration of the rotation animation
                easing = FastOutSlowInEasing, // Easing curve for the animation
            )
        )

        // Display a Card that rotates based on the flipped state
        Card(
            onClick = { setIsFlipped(!isFlipped) }, // Flip the card on click
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation.value // Apply rotation based on animation state
                    cameraDistance = 12f * density // Set the camera distance for the 3D effect
                },
        ) {
            if (rotation.value <= 90f) { // Check if the front side should be displayed
                Box(
                    Modifier.fillMaxSize() // Fill the entire space with the front content
                ) {
                    front() // Display the front content of the card
                }
            } else { // Show the back side if the card is flipped
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = 180f // Rotate to show the back side
                        },
                ) {
                    back() // Display the back content of the card
                }
            }
        }
    }


    // Function to preview the FlashcardItem
        @Preview
        @Composable
            fun FlashcardItem() {
                val context = LocalContext.current // Retrieve the current context
                val cardFace = CardFace(
                    frontText = "Front of card text will be here",
                    backText = "Back of card text will be here"
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(101,115,126)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FlipCard( // Insert the FlipCard composable here
                            cardFace = flashcardData ?: CardFace(
                                frontText = "Default front text",
                                backText = "Default back text"
                            ), // Provide a default value if flashcardData is null
                            modifier = Modifier.height(250.dp).width(350.dp),
                            back = { Text(flashcardData?.backText ?: "Default back text") },
                            front = { Text(flashcardData?.frontText ?: "Default front text") }
                        )
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
                                Icon(Icons.Filled.Add, "Add new card") // Display an icon on the FloatingActionButton
                            }
                        }
                    }
                }
            }

        }

