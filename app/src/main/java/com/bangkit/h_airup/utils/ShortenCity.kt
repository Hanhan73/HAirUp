package com.bangkit.h_airup.utils

object ShortenCity {
    fun shortenCityName(fullString: String): String {
        val words = fullString.split(" ")
        return buildString {
            if (words.isNotEmpty()) {
                val firstWord = words[0]
                append(if (firstWord.length >= 3) "${firstWord.substring(0, 3)}." else firstWord)

                for (i in 1 until words.size) {
                    append(" ") // Add space between words
                    append(words[i])
                }
            } else {
                append(fullString)
            }
        }
    }
}