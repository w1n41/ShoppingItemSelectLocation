package com.example.shoppingitemselectlocation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.shoppingitemselectlocation.utils.LocationUtils
import com.example.shoppingitemselectlocation.viewmodels.LocationViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MapDialog(viewModel: LocationViewModel, locationUtils: LocationUtils) {
    val pickedLocation by viewModel.pickedLocationData.collectAsState()
    val userLocation by viewModel.userLocationData.collectAsState()

    val defaultLocation = LatLng(40.7128, -74.0060)
    val latLng = userLocation?.let {
        LatLng(it.latitude, it.longitude)
    } ?: defaultLocation

    val cameraPosState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 10f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            cameraPositionState = cameraPosState,
            onMapClick = {
                viewModel.updatePickedLocation(it)
                locationUtils.getAddressFromPickedLocation(viewModel)
            }
        ) {
            val markerPosition = pickedLocation?.let {
                LatLng(it.latitude, it.longitude)
            } ?: latLng

            Marker(
                state = MarkerState(position = markerPosition)
            )
        }
    }
}

