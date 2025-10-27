package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import java.time.LocalDate
import java.time.Period

@Composable
fun AnniversaryPackScreen(event: Event) {
    val yearsTogether = Period.between(event.date, LocalDate.now()).years

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Years Together: $yearsTogether")
        Text(text = "Love Meter: 100%")
    }
}
