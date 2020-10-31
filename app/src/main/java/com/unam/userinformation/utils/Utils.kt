package com.unam.userinformation.utils

import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT = "yyyy-MM-dd"
const val DATE_FORMAT_PICKER = "dd/MM/yyyy"

@Suppress("TooGenericExceptionCaught")
inline fun <T> tryOrDefault(f: () -> T, defaultValue: T): T {
    return try {
        f()
    } catch (e: Exception) {
        e.printStackTrace()
        defaultValue
    }
}

@Suppress("TooGenericExceptionCaught")
inline fun tryOrPrintException(f: () -> Unit) {
    return try {
        f()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.oneLine() = this.trimIndent().replace("\n", "").unAccent()

val VOWELS = listOf('A', 'E', 'I', 'O', 'U')

val REGEX_UN_ACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun String.trimUpperCase(): String = this.trim().toUpperCase()

fun String.firstVowel(): Char = this.firstOrNull { it.isVowel() } ?: 'X'

fun Char.isVowel(): Boolean = VOWELS.contains(this)

fun CharSequence.unAccent(): String {
    val charSequenceX = this.replace("Ñ".toRegex(), "X")
    return REGEX_UN_ACCENT.replace(Normalizer.normalize(charSequenceX, Normalizer.Form.NFD), "")
}

fun getDatefromCalendarFormatDash(date: Date): String {
    return getDateWithFormat(
        SimpleDateFormat(DATE_FORMAT, Locale.US).format(date.time),
        DATE_FORMAT_PICKER
    )
}

private fun getDateWithFormat(date: String, format: String): String =
    getDateFromFormatToFormat(date, DATE_FORMAT, format)

private fun getDateFromFormatToFormat(
    date: String,
    initialFormat: String,
    finalFormat: String
): String {
    return tryOrDefault({
        val dateToFormat = SimpleDateFormat(initialFormat, Locale.US).parse(date)
        SimpleDateFormat(finalFormat, Locale.getDefault()).format(dateToFormat)
    }, "")
}