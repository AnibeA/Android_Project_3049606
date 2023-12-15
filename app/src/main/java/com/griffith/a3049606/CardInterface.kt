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


// Constants
const val DELTA_TIME: Float = 0.01f // Define DELTA_TIME as a constant with a value of 0.01 seconds (10 milliseconds)
const val THRESHOLD = 1.0f // Declare threshold as a constant

class CardInterface : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var gyroscopeListener: SensorEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setContent {
            val isFlippedState = remember { mutableStateOf(false) }

            FlashcardItem(isFlippedState)

            val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            gyroscopeListener = GyroscopeListener(isFlippedState)
            sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gyroscopeListener?.let { sensorManager.unregisterListener(it) }
    }

    private inner class GyroscopeListener(private val isFlippedState: MutableState<Boolean>) : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
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
                modifier = Modifier.padding(bottom = 50.dp) // Add padding at the bottom

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
                        .padding(bottom = 20.dp) // Add padding between card and button
                )
                AddCardButton()
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddCardButton() {
        val context = LocalContext.current
        FloatingActionButton(
            containerColor = Color(120, 200, 150),
            contentColor = Color.White,
            onClick = {
                val intent = Intent(context, AddDeck::class.java)
                context.startActivity(intent)
            }
        ) {
            Icon(Icons.Filled.Add, "Add new card")
        }
    }

    data class CardFace(val frontText: String, val backText: String)

    @Preview
    @Composable
    fun PreviewFlashcardItem() {
        FlashcardItem(remember { mutableStateOf(false) })
    }
}
