package com.example.shoppingitemselectlocation.viewmodels

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.shoppingitemselectlocation.data.LocationData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationViewModel: ViewModel() {
    private val _userLocationData = MutableStateFlow<LocationData?>(null)
    val userLocationData = _userLocationData.asStateFlow()
    private val _userLocationAddress = MutableStateFlow("")
//  val userLocationAddress = _userLocationAddress.asStateFlow()

    private val _pickedLocationData = MutableStateFlow<LocationData?>(null)
    val pickedLocationData = _pickedLocationData.asStateFlow()
    private val _pickedLocationAddress= MutableStateFlow("")
    val pickedLocationAddress = _pickedLocationAddress.asStateFlow()

    fun updatePickedLocation(latLng: LatLng){
        _pickedLocationData.value = LocationData(latitude = latLng.latitude, longitude = latLng.longitude)
    }

    fun updateUserLocation(lastLocation: Location?) {
        if (lastLocation != null){
            _userLocationData.value = LocationData(lastLocation.latitude, lastLocation.longitude)
        }
    }

    fun setUserAddress(address: String) {
        _userLocationAddress.value = address
    }

    fun setPickedLocationAddress(address: String) {
        _pickedLocationAddress.value = address
    }
}