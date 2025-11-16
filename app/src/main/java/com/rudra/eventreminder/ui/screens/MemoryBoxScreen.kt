package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.eventreminder.data.Memory
import com.rudra.eventreminder.viewmodel.MemoryViewModel

@Composable
fun MemoryBoxScreen(memoryViewModel: MemoryViewModel = viewModel()) {
    val memories by memoryViewModel.memories.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Memory Box")
        LazyColumn {
            items(memories) { memory ->
                Text(text = memory.notes)
            }
        }
        Button(onClick = { /* TODO: Add new memory */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add memory")
            Text(text = "Add Memory")
        }
    }
}
