package com.rudra.eventreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.rudra.eventreminder.ui.AppNavigation
import com.rudra.eventreminder.ui.theme.EventReminderTheme
import com.rudra.eventreminder.util.createNotificationChannel
import com.rudra.eventreminder.viewmodel.BackupViewModel
import com.rudra.eventreminder.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()
    private val backupViewModel: BackupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        setContent {
            val theme by themeViewModel.theme.collectAsState()
            EventReminderTheme(theme = theme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(themeViewModel = themeViewModel, backupViewModel = backupViewModel)
                }
            }
        }
    }
}
