package com.griffith.a3049606

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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

const val DELTA_TIME: Float = 0.01f // Define DELTA_TIME as a constant with a value of 0.01 seconds (10 milliseconds)

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
            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val

                    gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
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
        cardFace: CardFace,
        modifier: Modifier = Modifier,
        back: @Composable () -> Unit = {},
        front: @Composable () -> Unit = {},
    ) {
        // Remove unnecessary remember call
        val (isFlipped, setIsFlipped) = remember { mutableStateOf(false) } // Track the flipped state

        val rotation = animateFloatAsState(
            targetValue = if (isFlipped) 180f else 0f, // Set target rotation based on flipped state
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing,
            )
        )

        Card(
            onClick = { setIsFlipped(!isFlipped) }, // Update flipped state on click
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                },
        ) {
            if (rotation.value <= 90f) { // Check if front side should be shown
                Box(
                    Modifier.fillMaxSize()
                ) {
                    front()
                }
            } else { // Show back side if flipped
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = 180f
                        },
                ) {
                    back()
                }
            }
        }
    }


    // Function to preview the FlashcardItem
        @Preview
        @Composable
            fun FlashcardItem() {
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
                }
            }

        }

