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
fun BirthdayPackScreen(event: Event) {
    val age = Period.between(event.date, LocalDate.now()).years
    val nextBirthday = event.date.withYear(LocalDate.now().year).let {
        if (it.isBefore(LocalDate.now())) {
            it.plusYears(1)
        } else {
            it
        }
    }
    val starSign = getStarSign(event.date)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Age: $age")
        Text(text = "Next Birthday: $nextBirthday")
        Text(text = "Star Sign: $starSign")
    }
}

private fun getStarSign(date: LocalDate): String {
    return when (date.monthValue) {
        1 -> if (date.dayOfMonth <= 19) "Capricorn" else "Aquarius"
        2 -> if (date.dayOfMonth <= 18) "Aquarius" else "Pisces"
        3 -> if (date.dayOfMonth <= 20) "Pisces" else "Aries"
        4 -> if (date.dayOfMonth <= 19) "Aries" else "Taurus"
        5 -> if (date.dayOfMonth <= 20) "Taurus" else "Gemini"
        6 -> if (date.dayOfMonth <= 20) "Gemini" else "Cancer"
        7 -> if (date.dayOfMonth <= 22) "Cancer" else "Leo"
        8 -> if (date.dayOfMonth <= 22) "Leo" else "Virgo"
        9 -> if (date.dayOfMonth <= 22) "Virgo" else "Libra"
        10 -> if (date.dayOfMonth <= 22) "Libra" else "Scorpio"
        11 -> if (date.dayOfMonth <= 21) "Scorpio" else "Sagittarius"
        12 -> if (date.dayOfMonth <= 21) "Sagittarius" else "Capricorn"
        else -> "Unknown"
    }
}
