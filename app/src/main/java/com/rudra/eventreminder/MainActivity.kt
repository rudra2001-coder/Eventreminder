package com.rudra.eventreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.eventreminder.ui.AppNavHost
import com.rudra.eventreminder.ui.theme.EventReminderTheme
import com.rudra.eventreminder.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventReminderTheme() {
                val vm: EventViewModel = viewModel()
                AppNavHost(viewModel = vm)
            }
        }
    }
}
