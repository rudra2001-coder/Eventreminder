package com.rudra.eventreminder.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromTimeList(value: String?): List<LocalTime> {
        if (value == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<LocalTime>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toTimeList(list: List<LocalTime>): String {
        return Gson().toJson(list)
    }
}
