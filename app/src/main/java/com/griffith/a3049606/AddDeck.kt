package com.griffith.a3049606

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class AddDeck : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewDeck()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NewDeck() {
    // States for managing text input
    var deckName by remember { mutableStateOf(TextFieldValue("")) }
    var cardFront by remember { mutableStateOf(TextFieldValue("")) }
    var cardBack by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(101,115,126) // Background color
    ) {
        // Column layout for arranging elements vertically
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text field for entering the deck name
            Surface(
                modifier = Modifier
                    .height(70.dp)
                    .width(350.dp)
                    .padding(2.dp),
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            ) {

                TextField(

                    modifier = Modifier.fillMaxWidth(),
                    value = deckName,
                    onValueChange = { deckName = it },
                    label = { Text("Deck Name") },
                    placeholder = { Text("Enter deck name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }

            Spacer(modifier = Modifier.size(15.dp))

            Surface(
                modifier = Modifier
                    .height(100.dp)
                    .width(350.dp)
                    .padding(2.dp),
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cardFront,
                    onValueChange = { cardFront = it },
                    label = { Text("Front") },
                    placeholder = { Text("Front of card") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
            //space in between textfields
            Spacer(modifier = Modifier.size(10.dp))

            Surface(
                modifier = Modifier
                    .height(100.dp)
                    .width(350.dp)
                    .padding(2.dp),
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cardBack,
                    onValueChange = { cardBack = it },
                    label = { Text("Back") },
                    placeholder = { Text("Back of card") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        }
        Row( //set the row to the bottom right of the activity
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ){
            Surface(
                Modifier.padding(25.dp),
                //color = Color(120, 200, 150),
                shape = RoundedCornerShape(20.dp)
            ){
                Button(
                    onClick = {
                        // Handle the click event here
                        val dbHelper = DatabaseHelper(context)
                        //val deckId = dbHelper.insertDeck(deckName.text)
                        val deckNameText = deckName.text // Extract the text from TextFieldValue
                        if (deckNameText.isNotBlank()) {
                            val deckId = dbHelper.insertDeck(deckNameText)
                            if (deckId != -1L) {
                                dbHelper.insertCard(deckId, cardFront.text, cardBack.text)
                            }
                        }
                        // Navigate back to the MainActivity
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(Color(120,200,150)),
                    modifier = Modifier.size(90.dp, 40.dp)
                ) {
                    Text("Done")
                }
            }



        }

    }
}