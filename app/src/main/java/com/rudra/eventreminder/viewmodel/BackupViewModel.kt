package com.rudra.eventreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.backup.BackupManager
import com.rudra.eventreminder.data.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BackupViewModel(application: Application) : AndroidViewModel(application) {

    private val backupManager = BackupManager(application)

    private val _exportState = MutableStateFlow<Boolean?>(null)
    val exportState: StateFlow<Boolean?> = _exportState

    private val _importState = MutableStateFlow<List<Event>?>(null)
    val importState: StateFlow<List<Event>?> = _importState

    fun exportEvents(events: List<Event>) {
        viewModelScope.launch {
            _exportState.value = backupManager.exportEvents(events)
        }
    }

    fun importEvents() {
        viewModelScope.launch {
            _importState.value = backupManager.importEvents()
        }
    }
}
