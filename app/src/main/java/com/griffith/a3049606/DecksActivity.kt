package com.griffith.a3049606

import DatabaseHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.*


// Decks Activity - main activity for the Decks screen
class DecksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sets up the UI content for this activity
        setContent {
            DecksScreen() // Set the content to the DecksScreen composable function
        }
    }
}

// Preview function for the DecksScreen composable, used for design preview in Android Studio
@Preview
@Composable
fun DecksScreen() {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    val decksList = remember { mutableStateListOf<Pair<Long, String>>() }

    LaunchedEffect(Unit) {
        val cursor = dbHelper.getAllDecks()
        val tempList = mutableListOf<Pair<Long, String>>()
        while (cursor.moveToNext()) {
            val deckId = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val deckName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            tempList.add(Pair(deckId, deckName))
        }
        cursor.close()
        decksList.clear()
        decksList.addAll(tempList)
    }


    // Main surface layout for the Decks screen
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(101, 115, 126) // Background color
    ) {
        // Column layout for arranging items vertically
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // LazyColumn for displaying a scrollable list of items
            LazyColumn {
                items(decksList) { deck ->
                    ClickableItem(deck.second, context, deck.first) // deck.second is the name, deck.first is the ID
                }
            }
            AddDeckButton(context) // Now it's outside DecksScreen
        }
    }
}

    // Composable function for the Add Deck button
    @Composable
    fun AddDeckButton(context: Context) {
        // Row layout for positioning the button
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            // Surface for the button with padding and shape
            Surface(
                Modifier.padding(25.dp),
                color = Color(101, 115, 126),
                shape = RoundedCornerShape(10.dp)
            ) {
                // Floating action button with custom colors and click action
                FloatingActionButton(
                    containerColor = Color(120, 200, 150), // Button color
                    contentColor = Color.White, // Icon color
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                AddDeck::class.java
                            )
                        )
                    } // OnClick action
                ) {
                    Icon(Icons.Filled.Add, "New Deck") // Button icon
                }
            }
        }
    }

    // Composable function for a clickable list item
    @Composable
    fun ClickableItem(text: String, context: Context, deckId: Long) {
        // Surface for each list item with click action
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable {
                    val intent = Intent(context, CardInterface::class.java)
                    intent.putExtra("deckId", deckId)
                    context.startActivity(intent)
                },
            color = Color.White // Background color of the item
        ) {
            // Text inside the list item
            Text(
                text = text, // Display text
                color = Color.Black, // Text color
                modifier = Modifier.padding(16.dp) // Padding around the text
            )
        }
    }

