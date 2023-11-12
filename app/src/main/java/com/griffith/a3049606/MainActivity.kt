package com.griffith.a3049606

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Preview
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Surface(
        Modifier.fillMaxSize(),
        color = Color(150, 216, 250)
        ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            LazyColumn {
                item {
                    ClickableItem("Deck 1") {
                        // Handle item click, for example, navigate to another activity
                        val intent = Intent(context, CardInterface::class.java)
                        context.startActivity(intent)
                    }
                }
                item {
                    ClickableItem("Deck 2") {
                        // Handle item click, for example, navigate to another activity
                        val intent = Intent(context, CardInterface::class.java)
                        context.startActivity(intent)
                    }
                }
                // Add more items as needed
            }

        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                Modifier.padding(25.dp),
                color = Color(120, 200, 150),
                shape = RoundedCornerShape(10.dp)
            ) {
                FloatingActionButton(
                    containerColor = Color(150, 216, 250),
                    contentColor = Color.White,
                    onClick = {
                        val intent = Intent(context, AddDeck::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Icon(Icons.Filled.Add, "New Deck")
                }
            }
        }
    }
}

@Composable
fun ClickableItem(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onClick() },
        color = Color(120, 200, 150)
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}
