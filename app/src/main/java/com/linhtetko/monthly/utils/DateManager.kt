package com.linhtetko.monthly.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateManager {

    fun toPattern(time: Long?, pattern: String = "dd-MM-yyyy"): String? {
        if (time != null) {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            return sdf.format(time)
        }
        return null
    }
}