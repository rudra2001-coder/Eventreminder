package com.rudra.eventreminder.repository

import com.rudra.eventreminder.data.DAO.NoteDao
import com.rudra.eventreminder.data.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    fun getNotesForEvent(eventId: Int): Flow<List<Note>> {
        return noteDao.getNotesForEvent(eventId)
    }

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}
