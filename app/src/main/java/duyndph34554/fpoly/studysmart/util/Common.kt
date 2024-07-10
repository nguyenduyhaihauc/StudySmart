package duyndph34554.fpoly.studysmart.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import duyndph34554.fpoly.studysmart.presentation.theme.Green
import duyndph34554.fpoly.studysmart.presentation.theme.Orange
import duyndph34554.fpoly.studysmart.presentation.theme.Red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Green, value = 0),
    MEDIUM(title = "Medium", color = Orange, value = 1),
    HIGH(title = "High", color = Red, value = 2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM
    }
}

fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHours(): Float {
    val hours = this.toFloat() / 3600f
    return try {
        "%.2f".format(hours).replace(",", ".").toFloat()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0f
    }
}

sealed class SnackbarEvent{

    data class ShowSnackbar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarEvent()

    data object NavigateUp: SnackbarEvent()
}