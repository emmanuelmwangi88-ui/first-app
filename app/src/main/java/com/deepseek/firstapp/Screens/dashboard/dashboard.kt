package com.deepseek.firstapp.Screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deepseek.firstapp.Screens.HomeScreen.Homecard
import com.deepseek.firstapp.data.AuthViewModel
import com.deepseek.firstapp.navigation.ROUTE_ADDPRODUCT
import com.deepseek.firstapp.navigation.ROUTE_MYINTENT
import com.deepseek.firstapp.navigation.ROUTE_PRODUCTSLISTSCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val myauth = AuthViewModel(navController, context)
    Scaffold(
        // Top bar
        topBar = {
            TopAppBar(
                title = { Text("Welcome to SHOPIFY") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.Green,
                ),
                actions = {
                    IconButton(onClick = { myauth.logout() }) {
                        Icon(Icons.Filled.Person, contentDescription = "icon")
                    }//logout icon
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "close icon"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Settings, contentDescription = "icon")
                    }

                }
            )
        },

        // Floating Action Button
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(ROUTE_ADDPRODUCT) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },

        // Bottom bar
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Homecard(title = "Add product", background = Color.Gray, onClick = {navController.navigate(ROUTE_ADDPRODUCT)})
                Homecard(title = "Update product", background = Color.Gray, onClick = {})
            }
            Row() {
                Homecard(title = "Product list", background = Color.Gray, onClick = {navController.navigate(ROUTE_PRODUCTSLISTSCREEN)})
                Homecard(title = "my intents", background = Color.Gray, onClick = {navController.navigate(
                    ROUTE_MYINTENT
                )})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen(rememberNavController())
}
