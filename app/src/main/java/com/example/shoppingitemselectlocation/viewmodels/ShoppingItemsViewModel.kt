package com.example.shoppingitemselectlocation.viewmodels

import android.util.EventLogTags.Description
import androidx.lifecycle.ViewModel
import com.example.shoppingitemselectlocation.data.ShoppingItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Address

class ShoppingItemsViewModel(
): ViewModel() {
    private val _listOfShoppingItems = MutableStateFlow(listOf<ShoppingItemData>())
    val listOfShoppingItems = _listOfShoppingItems.asStateFlow()

    private val _nameOfShoppingItem = MutableStateFlow("")
    var nameOfShoppingItem = _nameOfShoppingItem.asStateFlow()

    private val _descriptionOfShoppingItem = MutableStateFlow("")
    var descriptionOfShoppingItem = _descriptionOfShoppingItem.asStateFlow()

    private val _addressOfShoppingItem = MutableStateFlow("")
    var addressOfShoppingItem = _addressOfShoppingItem.asStateFlow()

    private var id: Int = 1

    fun createShoppingItem(name: String, description: String, address: String){
        val shoppingList = _listOfShoppingItems.value.toMutableList()
        shoppingList.add(
            ShoppingItemData(
            id = id++,
            name = name,
            description = description,
            address = address
            )
        )
        _listOfShoppingItems.value = shoppingList.toList()
    }
}