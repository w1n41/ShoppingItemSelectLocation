package com.example.shoppingitemselectlocation.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.shoppingitemselectlocation.utils.LocationUtils
import com.example.shoppingitemselectlocation.viewmodels.LocationViewModel
import com.example.shoppingitemselectlocation.viewmodels.ShoppingItemsViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navHostController.navigate(Graph.ADD_DIALOG)
            }
        ) {
            Text("Add")
        }
        Spacer(Modifier.padding(vertical = 40.dp))
        Button(
            onClick = {
                navHostController.navigate(Graph.SECOND_SCREEN)
            }
        ) {
            Text("See list")
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
    navHostController: NavHostController,
    shoppingItemsViewModel: ShoppingItemsViewModel,
    locationViewModel: LocationViewModel,
    locationUtils: LocationUtils
) {
    val context = LocalContext.current

    val name = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val address by locationViewModel.pickedLocationAddress.collectAsState()
    val locationPermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true && permission[android.Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                locationUtils.getLocation()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity, android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        context, android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    Toast.makeText(context, "Permission denied!", Toast.LENGTH_LONG).show()
                }
            }
        }
    )
    BasicAlertDialog(
        onDismissRequest = {
            navHostController.navigate(Graph.MAIN_SCREEN)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name.value,
                onValueChange = {
                    name.value = it
                }
            )
            Spacer(Modifier.padding(vertical = 10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value,
                onValueChange = {
                    description.value = it
                }
            )
            Spacer(Modifier.padding(vertical = 10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                value = locationViewModel.pickedLocationAddress.value,
                onValueChange = {}
            )
            Spacer(Modifier.padding(vertical = 10.dp))
            Row {
                Button(
                    onClick = {
                        locationPermissions.launch(
                            arrayOf(
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                        navHostController.navigate(Graph.MAP_DIALOG)
                    }
                ) {
                    Text("Pick location")
                }
                Button(
                    onClick = {
                        shoppingItemsViewModel.createShoppingItem(name.value, description.value, address)
                        navHostController.navigate(Graph.MAIN_SCREEN)
                    }
                ) {
                    Text("Add")
                }
            }
        }
    }
}

