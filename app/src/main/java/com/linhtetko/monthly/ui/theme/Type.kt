package com.linhtetko.monthly.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.linhtetko.monthly.R

private val PadaukFont = FontFamily(
    Font(R.font.padauk_regular, FontWeight.ExtraLight),
    Font(R.font.padauk_regular, FontWeight.Light),
    Font(R.font.padauk_regular, FontWeight.Normal),
    Font(R.font.padauk_bold, FontWeight.Medium),
    Font(R.font.padauk_bold, FontWeight.Bold),
    Font(R.font.padauk_bold, FontWeight.ExtraBold),
)

private val DefaulFont = Typography(defaultFontFamily = PadaukFont)

val Typography = DefaulFont