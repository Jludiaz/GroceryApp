package com.example.groceryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groceryapp.ui.theme.GroceryAppTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sampleLists = listOf(
                GroceryList("Weekly Groceries", "Milk, Eggs, Bread, Fruit", "$42.13"),
                GroceryList("Pesto Recipe", "Pasta, Pesto, Cheese, Red Pepper Flakes", "$25.67"),
                GroceryList("Meal Prep", "Chicken, Rice, Vegetables", "$35.21"),
                GroceryList("Cleaning", "Soap, Sponges, Detergent", "$15.45")
            )

            GroceryAppTheme {
                HomeScreen(
                    groceryLists = sampleLists,
                    onListClick = { /* TODO: navigate to ListActivity */ },
                    onAddList = { /* TODO: open Add List dialog */ },
                    onNavigate = { /* TODO: handle bottom nav click */ }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    groceryLists: List<GroceryList>,
    onListClick: (GroceryList) -> Unit,
    onAddList: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddList,
                contentColor = Color(0xFF6cb3e6)//blue button
            ) {
                Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "Add List", modifier = Modifier.size(32.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomNavigationBar(onNavigate = onNavigate) //handle the navigation bar separately
        },
        containerColor = Color(0xFFF9F9F9) // off white background
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), //two elements per row (left to right)
            contentPadding = innerPadding, //pass inner padding
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            //padding outside box for screen margins
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(groceryLists) { list ->
                GroceryListCard(list = list, onClick = { onListClick(list) })
            }
        }
    }
}

@Composable
fun GroceryListCard(list: GroceryList, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(Color(0xFF6cb3e6)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = list.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 1,
            )
            Text(
                text = list.description,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 2
            )
            Spacer(modifier = Modifier.weight(1f))//place at the bottom of the card
            Text(
                text = "Total: ${list.totalCost}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun BottomNavigationBar(onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate("home") },
            icon = { Icon(painterResource(id = R.drawable.home), contentDescription = "Home", modifier = Modifier.size(32.dp)) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate("inventory") },
            icon = { Icon(painterResource(id = R.drawable.inventory), contentDescription = "Inventory", modifier = Modifier.size(32.dp)) },
            label = { Text("Inventory") },

        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate("profile") },
            icon = { Icon(painterResource(id = R.drawable.profile), contentDescription = "Profile", modifier = Modifier.size(32.dp)) },
            label = { Text("Profile") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val sampleLists = listOf(
        GroceryList("Weekly Groceries", "Milk, Eggs, Bread, Fruit", "$42.13"),
        GroceryList("Party Supplies", "Snacks, Drinks, Plates", "$25.67"),
        GroceryList("Meal Prep", "Chicken, Rice, Vegetables", "$35.21"),
        GroceryList("Cleaning", "Soap, Sponges, Detergent", "$15.45")
    )

    GroceryAppTheme {
        HomeScreen(
            groceryLists = sampleLists,
            onListClick = { /* TODO: navigate to ListActivity */ },
            onAddList = { /* TODO: open Add List dialog */ },
            onNavigate = { /* TODO: handle bottom nav click */ }
        )
    }
}



