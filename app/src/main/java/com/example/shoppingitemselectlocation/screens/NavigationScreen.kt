package com.example.shoppingitemselectlocation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shoppingitemselectlocation.utils.LocationUtils
import com.example.shoppingitemselectlocation.viewmodels.LocationViewModel
import com.example.shoppingitemselectlocation.viewmodels.ShoppingItemsViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationScreen(
    shoppingItemsViewModel: ShoppingItemsViewModel,
    locationViewModel: LocationViewModel,
    modifier: Modifier
){
    val navController = rememberNavController()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context, locationViewModel)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Graph.MAIN_SCREEN
    ){
        composable(route = Graph.MAIN_SCREEN) {
            MainScreen(navController)
        }
        composable(route = Graph.SECOND_SCREEN) {
            SecondScreen(navController, shoppingItemsViewModel)
        }
        dialog(route = Graph.ADD_DIALOG){
            AddDialog(navController,
                shoppingItemsViewModel,
                locationViewModel,
                locationUtils
            )
        }
        dialog(route = Graph.MAP_DIALOG){
            MapDialog(locationViewModel, locationUtils)
        }
    }
}

object Graph{
    const val MAIN_SCREEN = "MainScreen"
    const val SECOND_SCREEN = "SecondScreen"
    const val ADD_DIALOG = "AddDialog"
    const val MAP_DIALOG = "MapDialog"
}