package rs.appsterdam.app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    private val inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    
    @RequiresApi(Build.VERSION_CODES.O)
    private val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatEventDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val cleanDate = dateString.split(":")[0]
            val localDateTime = LocalDateTime.parse(cleanDate, inputFormatter)
            localDateTime.format(outputFormatter)
        } catch (_: Exception) {
            dateString
        }
    }
}
