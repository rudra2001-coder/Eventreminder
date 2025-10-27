package com.rudra.eventreminder.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.util.DateUtils
import com.rudra.eventreminder.viewmodel.EventViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemoryScreen(viewModel: EventViewModel, onDone: () -> Unit) {
    val ctx = LocalContext.current
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("General") }
    var emoji by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var reminderTimes by remember { mutableStateOf(listOf<LocalTime>()) }
    var recur by remember { mutableStateOf(true) }
    var showReminderDialog by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        ctx,
        { _: DatePicker, y, m, d ->
            val selectedDate = LocalDate.of(y, m + 1, d)
            date = selectedDate
            if (DateUtils.daysLeft(selectedDate, false) in 0..7) {
                showReminderDialog = true
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePicker = TimePickerDialog(
        ctx,
        { _, hour, min ->
            reminderTimes = reminderTimes + LocalTime.of(hour, min)
        },
        9, 0, true
    )

    if (showReminderDialog) {
        AlertDialog(
            onDismissRequest = { showReminderDialog = false },
            title = { Text("Set Reminder") },
            text = { Text("This event is within 7 days. Do you want to set a reminder for it?") },
            confirmButton = {
                TextButton(onClick = { showReminderDialog = false }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { reminderTimes = emptyList(); showReminderDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Memory", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    eventType = when {
                        it.contains("birthday", ignoreCase = true) -> "Birthday"
                        it.contains("anniversary", ignoreCase = true) -> "Anniversary"
                        it.contains("bill", ignoreCase = true) -> "Bill"
                        it.contains("pay", ignoreCase = true) -> "Payment"
                        else -> "General"
                    }
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
             OutlinedTextField(
                value = eventType,
                onValueChange = { },
                label = { Text("Event Type") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = emoji, onValueChange = { emoji = it }, label = { Text("Emoji") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            Row {
                Button(onClick = { datePicker.show() }) { Text("Pick date: ${date.toString()}") }
            }
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = recur, onCheckedChange = { recur = it })
                Text("Recurring yearly")
            }
            Spacer(Modifier.height(16.dp))

            Text("Reminders", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(reminderTimes) { time ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(time.toString())
                        IconButton(onClick = { reminderTimes = reminderTimes - time }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete reminder")
                        }
                    }
                }
            }
            Button(onClick = { timePicker.show() }) {
                Icon(Icons.Default.Add, contentDescription = "Add reminder")
                Spacer(Modifier.width(8.dp))
                Text("Add reminder")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isBlank() || emoji.isBlank()) return@Button
                    val event = Event(
                        title = title,
                        description = desc,
                        date = date,
                        eventType = eventType,
                        emoji = emoji,
                        reminderTimes = reminderTimes,
                        isRecurring = recur
                    )
                    viewModel.addEvent(event)
                    onDone()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Save", color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}
