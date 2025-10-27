package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event

@Composable
fun WorkPackScreen(event: Event) {
    var tasks by remember { mutableStateOf(listOf("Task 1", "Task 2", "Task 3")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Deadline: ${event.date}")
        Text(text = "Tasks:")
        tasks.forEach { task ->
            var isChecked by remember { mutableStateOf(false) }
            Row {
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
                Text(text = task)
            }
        }
        Text(text = "Streak: 5 days")
    }
}
