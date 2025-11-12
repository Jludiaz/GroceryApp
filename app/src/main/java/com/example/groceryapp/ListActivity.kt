package com.example.groceryapp

import androidx.compose.material3.ListItem
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ListItemDefaults.containerColor
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groceryapp.ui.theme.GroceryAppTheme

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroceryAppTheme {
                DisplayList(
                    onAddList = { /* TODO: open Add List popup */ },
                    onNavigate = { /* TODO: handle bottom nav click */ }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayList(
    onAddList: () -> Unit,
    onNavigate: (String) -> Unit
){

    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFFF9F9F9),
                    titleContentColor = Color(0xFF6cb3e6),
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(onNavigate = onNavigate) //handle the navigation bar separately
        },
        containerColor = Color(0xFFF9F9F9) // off white background
    ){ innerPadding ->
        val textFieldState = rememberTextFieldState() //Search query state
        val searchResultsList: MutableList<String> = mutableListOf("Dog", "Cat", "Bird") //Fake list of results

        SimpleSearchBar(
            textFieldState = textFieldState,
            onSearch = {/* search API here */ },
            searchResults = searchResultsList,
            modifier = Modifier.padding(top = 30.dp)
        )

        var selectedItem by remember { mutableStateOf<String?>(null) }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            contentPadding = innerPadding
        ) {
            items(searchResultsList) { resultItem ->
                val isSelected = resultItem == selectedItem
                ListItem(
                    headlineContent = { Text(resultItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = ListItemDefaults.colors(
                        containerColor = if (isSelected) Color(0xFF4CAF50) else Color(0xFFF5F5F5), // green if selected, gray if not
                        headlineColor = if (isSelected) Color(0xFFFFFFFF) else Color(0xFF000000)
                    )
                )
            }
        }
    }
}

/*Credit to developer.android.com for experimental search bar code*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f }
                .padding(30.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // Display search results in a scrollable column
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListActivityPreview(){
    GroceryAppTheme {
        DisplayList(
            onAddList = { /* TODO: open Add List popup */ },
            onNavigate = { /* TODO: handle bottom nav click */ }
        )
    }
}