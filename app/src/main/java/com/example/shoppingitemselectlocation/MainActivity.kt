package com.example.shoppingitemselectlocation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.shoppingitemselectlocation.screens.NavigationScreen
import com.example.shoppingitemselectlocation.ui.theme.ShoppingItemSelectLocationTheme
import com.example.shoppingitemselectlocation.viewmodels.LocationViewModel
import com.example.shoppingitemselectlocation.viewmodels.ShoppingItemsViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingItemSelectLocationTheme {
                val locationViewModel = LocationViewModel()
                val shoppingItemsViewModel = ShoppingItemsViewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationScreen(shoppingItemsViewModel, locationViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
