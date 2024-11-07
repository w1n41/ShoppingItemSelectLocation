package com.example.shoppingitemselectlocation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shoppingitemselectlocation.data.ShoppingItemData
import com.example.shoppingitemselectlocation.viewmodels.ShoppingItemsViewModel

@Composable
fun SecondScreen(
    navHostController: NavHostController,
    shoppingItemsViewModel: ShoppingItemsViewModel
){
    val listOfShoppingItems = shoppingItemsViewModel.listOfShoppingItems.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.width(300.dp),
            onClick = {
                navHostController.navigate(Graph.MAIN_SCREEN)
            }
        ) {
            Text("Back")
        }
        LazyColumn {
            items(listOfShoppingItems.value){
                ShoppingItem(it)
            }
        }
    }

}

@Composable
fun ShoppingItem(
    shoppingItemData: ShoppingItemData
){
    Card(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
        border = BorderStroke(2.dp, color = Color.DarkGray),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Name: "
                )
                Text(
                    text = shoppingItemData.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "Description: "
                )
                Text(
                    text = shoppingItemData.description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "Address: "
                )
                Text(
                    text = shoppingItemData.address,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
