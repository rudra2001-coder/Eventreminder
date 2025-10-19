package com.rudra.eventreminder.util

object EmojiUtils {
    fun getAvailableEmojis(): List<String> {
        // A selection of emojis for the user to choose from
        return listOf(
            "\uD83C\uDF89", "\uD83C\uDF82", "\uD83E\uDD73", "\uD83C\uDF8A", "\uD83C\uDF81",
            "\uD83D\uDC9D", "\uD83D\uDC8C", "\uD83C\uDF93", "\uD83C\uDF92", "\uD83D\uDE4C",
            "\uD83D\uDC4F", "\uD83D\uDE0A", "\uD83D\uDE0D", "\uD83D\uDE18", "\uD83D\uDC4D"
        )
    }
}
