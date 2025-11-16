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
import com.rudra.eventreminder.data.Habit
import com.rudra.eventreminder.viewmodel.HabitViewModel
import java.time.LocalDate

@Composable
fun HabitsScreen(habitViewModel: HabitViewModel = viewModel()) {
    val habits by habitViewModel.habits.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Habits")
        LazyColumn {
            items(habits) { habit ->
                var isChecked by remember { mutableStateOf(habit.lastCompletedDate == LocalDate.now()) }
                Row {
                    Checkbox(checked = isChecked, onCheckedChange = { 
                        isChecked = it
                        if (isChecked) {
                            habitViewModel.updateHabit(habit.copy(streak = habit.streak + 1, lastCompletedDate = LocalDate.now()))
                        } else {
                            habitViewModel.updateHabit(habit.copy(streak = habit.streak - 1, lastCompletedDate = habit.lastCompletedDate?.minusDays(1)))
                        }
                    })
                    Text(text = "${habit.name} - Streak: ${habit.streak}")
                    IconButton(onClick = { habitViewModel.deleteHabit(habit) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete habit")
                    }
                }
            }
        }
        Button(onClick = { /* TODO: Add new habit */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add habit")
            Text(text = "Add Habit")
        }
    }
}
