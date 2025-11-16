package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.eventreminder.data.Gift
import com.rudra.eventreminder.viewmodel.GiftViewModel

@Composable
fun GiftReminderScreen(eventId: Int, giftViewModel: GiftViewModel = viewModel()) {
    val gifts by giftViewModel.getGiftsForEvent(eventId).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Gift Ideas")
        LazyColumn {
            items(gifts) { gift ->
                Row {
                    var isChecked by remember { mutableStateOf(gift.isPurchased) }
                    Checkbox(checked = isChecked, onCheckedChange = { 
                        isChecked = it
                        giftViewModel.updateGift(gift.copy(isPurchased = it))
                    })
                    Text(text = "${gift.name} - $${gift.budget}")
                    IconButton(onClick = { giftViewModel.deleteGift(gift) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete gift")
                    }
                }
            }
        }
        Button(onClick = { /* TODO: Add new gift */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add gift")
            Text(text = "Add Gift")
        }
    }
}
