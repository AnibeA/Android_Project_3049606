package com.griffith.a3049606

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun HomeScreen(){
    val context = LocalContext.current
    Surface(Modifier.fillMaxSize()
        )  {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment= Alignment.Start
        ){
            Surface(Modifier.padding(20.dp),
                    color = Color(120,200,150),
                    shape = RoundedCornerShape(10.dp)
                    ){
                Text("Mobile Development", modifier = Modifier.padding(10.dp))
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
                    onClick = {val intent = Intent(context, AddDeck::class.java)
                    context.startActivity(intent)
                    }){
                    Icon(Icons.Filled.Add, "New Deck")
                }
            }
        }
    }

}
