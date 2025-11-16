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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.viewmodel.EventViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@Composable
fun AddEditEventScreen(viewModel: EventViewModel, onDone: () -> Unit, eventId: Long) {
    val event = if (eventId != -1L) {
        viewModel.getEvent(eventId).collectAsState(initial = null).value
    } else {
        null
    }

    val ctx = LocalContext.current
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf<LocalTime?>(LocalTime.of(9, 0)) }
    var recur by remember { mutableStateOf(true) }

    LaunchedEffect(event) {
        event?.let {
            title = it.title
            desc = it.description ?: ""
            emoji = it.emoji
            date = it.date
            time = it.reminderTimes.firstOrNull()
            recur = it.isRecurring
            eventType = it.eventType
        }
    }

    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        ctx,
        { _: DatePicker, y, m, d ->
            date = LocalDate.of(y, m + 1, d)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePicker = TimePickerDialog(
        ctx,
        { _, hour, min ->
            time = LocalTime.of(hour, min)
        },
        9, 0, true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = emoji, onValueChange = { emoji = it }, label = { Text("Emoji") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = eventType, onValueChange = { eventType = it }, label = { Text("Event Type") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { datePicker.show() }) { Text("Pick date: ${date.toString()}") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { timePicker.show() }) { Text("Pick time: ${time?.toString() ?: "None"}") }
        }
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = recur, onCheckedChange = { recur = it })
            Text("Recurring yearly")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isBlank() || emoji.isBlank()) return@Button
                val updatedEvent = Event(
                    id = event?.id ?: 0,
                    title = title,
                    description = desc,
                    date = date,
                    emoji = emoji,
                    reminderTimes = time?.let { listOf(it) } ?: emptyList(),
                    isRecurring = recur,
                    eventType = eventType
                )
                if (event == null) {
                    viewModel.addEvent(updatedEvent)
                } else {
                    viewModel.updateEvent(updatedEvent)
                }
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}
