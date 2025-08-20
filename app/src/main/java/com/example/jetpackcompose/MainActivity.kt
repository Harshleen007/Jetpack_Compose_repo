package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("second") { SecondScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate("second") }) {
            Text("Go to Next Screen")
        }
    }
}

@Composable
fun SecondScreen(navController: NavHostController) {
    var selected by remember { mutableStateOf(-1) }
    var selectedDialogIndex by remember { mutableStateOf(-1) }

    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (selected != -1) colors[selected] else Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("⬅ Back")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Row for horizontal buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) { index ->

                val boxSize = animateDpAsState(
                    targetValue = if (selected == index) 80.dp else 60.dp
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .height(boxSize.value)
                        .background(
                            if (selected == index) Color.Black else Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selected = index
                            selectedDialogIndex = index // open dialog
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Btn ${index + 1}",
                        color = if (selected == index) Color.White else Color.Black
                    )
                }
            }
        }
    }

    // Show dialog when button clicked
    if (selectedDialogIndex != -1) {
        AlertDialog(
            onDismissRequest = { selectedDialogIndex = -1 },
            title = { Text("Button Clicked") },
            text = { Text("You clicked Button ${selectedDialogIndex + 1}") },
            confirmButton = {
                TextButton(onClick = { selectedDialogIndex = -1 }) {
                    Text("OK")
                }
            }
        )
    }
}
