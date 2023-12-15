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
        // Initializing the sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setContent {
            val isFlippedState = remember { mutableStateOf(false) }

            FlashcardItem(isFlippedState)

            val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            gyroscopeListener = GyroscopeListener(isFlippedState)
            sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    // onDestroy method called when the activity is destroyed

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the gyroscope listener

        gyroscopeListener?.let { sensorManager.unregisterListener(it) }
    }
    // Inner class to handle gyroscope events for card flipping

    private inner class GyroscopeListener(private val isFlippedState: MutableState<Boolean>) : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            // Reacting to gyroscope changes
            if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
                val rotationRateY = event.values[1]
                if (Math.abs(rotationRateY) > THRESHOLD) {
                    runOnUiThread {
                        isFlippedState.value = !isFlippedState.value
                    }
                }
            }
        }
    }

    @Composable
    fun FlashcardItem(isFlippedState: MutableState<Boolean>) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(101, 115, 126)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                FlipCard(
                    isFlippedState = isFlippedState,
                    cardFace = CardFace(
                        frontText = "Front of card text will be here",
                        backText = "Back of card text will be here"
                    ),
                    modifier = Modifier
                        .height(250.dp)
                        .width(350.dp)
                        .padding(bottom = 20.dp)
                )
                AddCardButton() // Button to add a new card
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    // Composable function to create the flip effect on the card
    fun FlipCard(
        isFlippedState: MutableState<Boolean>,
        cardFace: CardFace,
        modifier: Modifier = Modifier
    ) {
        val rotation = animateFloatAsState(
            targetValue = if (isFlippedState.value) 180f else 0f,
            animationSpec = tween(durationMillis = 400)
        )

        Card(
            onClick = { isFlippedState.value = !isFlippedState.value },
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                }
        ) {
            if (rotation.value <= 90f) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = cardFace.frontText, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = cardFace.backText, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                }
            }
        }
    }

    // Composable function for the Add Card button with a dialog popup

    @Composable
    fun AddCardButton() {
        var showDialog by remember { mutableStateOf(false) }

        FloatingActionButton(
            containerColor = Color(120, 200, 150),
            contentColor = Color.White,
            onClick = { showDialog = true }
        ) {
            Icon(Icons.Filled.Add, "Add new card")
        }

        if (showDialog) {
            CardInputDialog(onDismiss = { showDialog = false })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CardInputDialog(onDismiss: () -> Unit) {
        var frontText by remember { mutableStateOf("") }
        var backText by remember { mutableStateOf("") }

        // Define the green color for the app
        val appGreen = Color(120, 200, 150)

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Add New Card") },
            text = {
                Column {
                    // Rounded corner text field for 'Front'
                    TextField(
                        value = frontText,
                        onValueChange = { frontText = it },
                        label = { Text("Front") },
                        shape = RoundedCornerShape(8.dp), // Rounded corners
                        colors = TextFieldDefaults.textFieldColors(
                            Color.White,
                            focusedIndicatorColor = appGreen, // Bottom line color when focused
                            unfocusedIndicatorColor = appGreen // Bottom line color when not focused
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Rounded corner text field for 'Back'
                    TextField(
                        value = backText,
                        onValueChange = { backText = it },
                        label = { Text("Back") },
                        shape = RoundedCornerShape(8.dp), // Rounded corners
                        colors = TextFieldDefaults.textFieldColors(
                             Color.White,
                            focusedIndicatorColor = appGreen, // Bottom line color when focused
                            unfocusedIndicatorColor = appGreen // Bottom line color when not focused
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle the confirmation action here
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(appGreen)
                ) {
                    Text("Add", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = appGreen)
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    data class CardFace(val frontText: String, val backText: String)

    @Preview
    @Composable
    fun PreviewFlashcardItem() {
        FlashcardItem(remember { mutableStateOf(false) })
    }
}