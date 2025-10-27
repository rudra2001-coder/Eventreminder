package com.rudra.eventreminder.viewmodel

import androidx.lifecycle.ViewModel
import com.rudra.eventreminder.ui.theme.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {
    private val _theme = MutableStateFlow(Theme.DEFAULT)
    val theme: StateFlow<Theme> = _theme

    fun setTheme(theme: Theme) {
        _theme.value = theme
    }
}
