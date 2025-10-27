package com.rudra.eventreminder.backup

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rudra.eventreminder.data.Event
import java.io.File

class BackupManager(private val context: Context) {

    fun exportEvents(events: List<Event>): Boolean {
        return try {
            val gson = Gson()
            val json = gson.toJson(events)
            val file = File(context.filesDir, "events_backup.json")
            file.writeText(json)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun importEvents(): List<Event>? {
        return try {
            val file = File(context.filesDir, "events_backup.json")
            if (!file.exists()) {
                return null
            }
            val json = file.readText()
            val gson = Gson()
            val type = object : TypeToken<List<Event>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
