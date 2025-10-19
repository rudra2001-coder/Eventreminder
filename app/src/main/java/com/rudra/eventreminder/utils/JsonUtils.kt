package com.rudra.eventreminder.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rudra.eventreminder.data.Emoji
import java.io.IOException

fun getEmojisJsonDataFromAsset(context: Context): List<Emoji>? {
    val jsonString: String
    try {
        jsonString = context.assets.open("emojis.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    val listEmojiType = object : TypeToken<List<Emoji>>() {}.type
    return Gson().fromJson(jsonString, listEmojiType)
}
