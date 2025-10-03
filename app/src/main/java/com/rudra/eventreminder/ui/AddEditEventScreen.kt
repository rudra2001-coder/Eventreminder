package com.rudra.eventreminder.ui



import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.viewmodel.EventViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun AddEditEventScreen(viewModel: EventViewModel, onDone: () -> Unit) {
    val ctx = LocalContext.current
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf<LocalTime?>(LocalTime.of(9,0)) }
    var recur by remember { mutableStateOf(true) }

    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(ctx, { _: DatePicker, y, m, d ->
        date = LocalDate.of(y, m + 1, d)
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    val timePicker = TimePickerDialog(ctx, { _, hour, min ->
        time = LocalTime.of(hour, min)
    }, 9, 0, true)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { datePicker.show() }) { Text("Pick date: ${date.toString()}") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { timePicker.show() }) { Text("Pick time: ${time?.toString() ?: "None"}") }
        }
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = recur, onCheckedChange = { recur = it })
            Text("Recurring yearly")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (title.isBlank()) return@Button
            val event = Event(
                title = title,
                description = desc.ifBlank { null },
                date = date,
                reminderTime = time,
                isRecurring = recur
            )
            viewModel.addEvent(event)
            onDone()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
    }
}
