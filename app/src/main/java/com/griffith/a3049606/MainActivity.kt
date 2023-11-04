@file:OptIn(ExperimentalMaterial3Api::class)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Activity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Activity1Content()
        }
    }
}

@Composable
fun Activity1Content() {
    var selectedCategory by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Business Categories") }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Apply content padding here
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(getSampleCategories()) { category ->
                        CategoryItem(category) { selectedCategory = category }
                    }
                }
            }
        },
    )
    Now, the contentPadding is applied to the Column that contains the LazyColumn. This should ensure that there is padding around the content within the Scaffold.







    @Composable
fun CategoryList(categories: List<String>, onCategorySelected: (String) -> Unit) {
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category, onItemClick = { onCategorySelected(category) })
        }
    }
}

@Composable
fun CategoryItem(category: String, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = category, style = MaterialTheme.typography.h6)
        }
    }
}

private fun getSampleCategories(): List<String> {
    return listOf("Restaurants", "Shops", "Services", "Entertainment", "Health")
}


