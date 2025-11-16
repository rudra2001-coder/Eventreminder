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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.eventreminder.data.Note
import com.rudra.eventreminder.viewmodel.NoteViewModel

@Composable
fun NotesScreen(eventId: Int, noteViewModel: NoteViewModel = viewModel()) {
    val notes by noteViewModel.getNotesForEvent(eventId).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Notes")
        LazyColumn {
            items(notes) { note ->
                Row {
                    Text(text = note.title)
                    IconButton(onClick = { noteViewModel.deleteNote(note) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete note")
                    }
                }
            }
        }
        Button(onClick = { /* TODO: Add new note */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add note")
            Text(text = "Add Note")
        }
    }
}
