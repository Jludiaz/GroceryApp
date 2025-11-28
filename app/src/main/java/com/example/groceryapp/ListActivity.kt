package com.example.groceryapp

import androidx.compose.material3.ListItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groceryapp.ui.theme.GroceryAppTheme

class ListActivity : ComponentActivity() {
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val groceryList = intent.getParcelableExtra<GroceryList>("list") ?: return

        // Code by ChatGPT to
        val viewModel: ListViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return ListViewModel(groceryList) as T
                }
            }
        }

        setContent {
            GroceryAppTheme {
                DisplayList(
                    viewModel = viewModel,
                    onNavigate = { /* TODO: handle bottom nav click */ }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayList(
    viewModel: ListViewModel,
    onNavigate: (String) -> Unit
){
    val items = viewModel.items
    val totalCost = viewModel.totalCost()

    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFFF9F9F9),
                    titleContentColor = Color(0xFF6cb3e6),
                ),
                title = {
                    Text("${viewModel.title}")
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(onNavigate = onNavigate) //handle the navigation bar separately
        },
        containerColor = Color(0xFFF9F9F9) // off white background
    ){ innerPadding ->
        Column(Modifier.padding(innerPadding)) {

            // Show total cost
            Text(
                text = "Total: $${"%.2f".format(totalCost)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(items) { item ->

                    ListItem(
                        headlineContent = { Text(item.title) },
                        supportingContent = { Text("$${item.cost}") },
                        trailingContent = {
                            IconButton(onClick = { viewModel.removeItem(item) }) {
                                Icon(
                                    painterResource(R.drawable.ic_launcher_background),
                                    contentDescription = "Remove"
                                )
                            }
                        }
                    )
                }
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
fun ListActivityPreview() {
    // Create a sample GroceryList
    val sampleGroceryList = GroceryList(
        title = "Sample Grocery List",
        description = "This is a preview list",
        items = listOf(
            ProductList("Example Item", 1.99),
            ProductList("Another Item", 3.49)
        ),
        totalCost = "20"
    )

    // Pass the GroceryList to the ViewModel
    val vm = ListViewModel(sampleGroceryList)

    GroceryAppTheme {
        DisplayList(
            viewModel = vm,
            onNavigate = {}
        )
    }
}