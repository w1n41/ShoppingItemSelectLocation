package com.example.shoppingitemselectlocation.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.shoppingitemselectlocation.viewmodels.LocationViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationUtils(
    private val context: Context,
    private val locationViewModel: LocationViewModel
) {
    private val _fusedProvider = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.Builder(1000).build()
    private val userLocationCallback = object: LocationCallback(){
        @SuppressLint("NewApi")
        override fun onLocationResult(location: LocationResult) {
            super.onLocationResult(location)
            locationViewModel.updateUserLocation(location.lastLocation)
            getAddressFromLocation(locationViewModel)
        }
    }
    private fun isLocationPermissionGranted(): Boolean{
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        if (isLocationPermissionGranted()){
            _fusedProvider.requestLocationUpdates(
                locationRequest, userLocationCallback, Looper.getMainLooper()
            )
        }
    }

//    fun stopGettingLocation(){
//        _fusedProvider.removeLocationUpdates(userLocationCallback)
//    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getAddressFromLocation(locationViewModel: LocationViewModel){
        val locationData = locationViewModel.userLocationData.value
        if (locationData != null){
            val geocoder = Geocoder(context, Locale.getDefault())
            geocoder.getFromLocation(
                locationData.latitude, locationData.longitude, 1,
                (Geocoder.GeocodeListener { addresses ->
                    val address = addresses[0].getAddressLine(0)
                    if (address != null){
                        locationViewModel.setUserAddress(address)
                    }
                })
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getAddressFromPickedLocation(locationViewModel: LocationViewModel){
        val locationData = locationViewModel.pickedLocationData.value
        if (locationData != null){
            val geocoder = Geocoder(context, Locale.getDefault())
            geocoder.getFromLocation(
                locationData.latitude, locationData.longitude, 1

            ) { addresses ->
                val address = addresses[0].getAddressLine(0)
                if (address != null) {
                    locationViewModel.setPickedLocationAddress(address)
                }
            }
        }
    }

}