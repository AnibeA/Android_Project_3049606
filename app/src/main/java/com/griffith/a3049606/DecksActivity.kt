package com.griffith.a3049606

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Decks Activity
class DecksActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            DecksScreen() // Set the content to the DecksScreen composable function
        }
    }
}

// Preview function for the DecksScreen composable
@Preview
@Composable
fun DecksScreen() {
    val context = LocalContext.current // Retrieve the current context

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(101, 115, 126)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            LazyColumn {
                item { ClickableItem("Deck 1", context) }
                item { ClickableItem("Deck 2", context) }
                // Add more items as needed
            }
        }

        AddDeckButton(context)
    }
}

@Composable
fun AddDeckButton(context: Context) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            Modifier.padding(25.dp),
            color = Color(101, 115, 126),
            shape = RoundedCornerShape(10.dp)
        ) {
            FloatingActionButton(
                containerColor = Color(120, 200, 150),
                contentColor = Color.White,
                onClick = { context.startActivity(Intent(context, AddDeck::class.java)) }
            ) {
                Icon(Icons.Filled.Add, "New Deck")
            }
        }
    }
}

@Composable
fun ClickableItem(text: String, context: Context) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable {
                context.startActivity(Intent(context, CardInterface::class.java))
            },
        color = Color.White
    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}