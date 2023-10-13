package com.linhtetko.monthly.ui.theme

import androidx.compose.ui.graphics.Color

val Red500 = Color(0xFFF44336)
val Teal500 = Color(0xFF009688)
val Pink500 = Color(0xFFE91E63)
val Green500 = Color(0xFF4CAF50)
val Purple500 = Color(0xFF9C27B0)
val LightGreen500 = Color(0xFF8BC34A)
val DeepPurple500 = Color(0xFF673AB7)
val Lime500 = Color(0xFFCDDC39)
val Indigo500 = Color(0xFF3F51B5)
val Yellow500 = Color(0xFFFFEB3B)
val Blue500 = Color(0xFF2196F3)
val Amber500 = Color(0xFFFFC107)
val SkyBlue500 = Color(0xFF03A9F4)
val Orange500 = Color(0xFFFF9800)
val Cyan500 = Color(0xFF00BCD4)
val DeepOrange500 = Color(0xFFFF5722)

val White = Color(0xFFFFFFFF)
val Dark = Color(0xFF333333)

data class ColorItem(
    val code: String,
    val color: Color,
    val onColor: Color
){
    companion object{

        const val DarkCode = "dark"
        const val LightCode = "light"

        private const val Red500Code = "red-500"
        private const val Teal500Code = "teal-500"
        private const val Pink500Code = "pink-500"
        const val Green500Code = "green-500"
        private const val Purple500Code = "purple-500"
        private const val LightGreen500Code = "light-green-500"
        private const val DeepPurple500Code = "deep-purple-500"
        private const val Lime500Code = "lime-500"
        private const val Indigo500Code = "indigo-500"
        private const val Yellow500Code = "yellow-500"
        private const val Blue500Code = "blue-500"
        private const val Amber500Code = "amber-500"
        private const val SkyBlue500Code = "sky-blue-500"
        private const val Orange500Code = "orange-500"
        private const val Cyan500Code = "cyan-500"
        private const val DeepOrange500Code = "deep-orange-500"

        val colors = listOf(
            ColorItem(
                code = Red500Code,
                color = Red500,
                onColor = White
            ),
            ColorItem(
                code = Teal500Code,
                color = Teal500,
                onColor = White
            ),
            ColorItem(
                code = Pink500Code,
                color = Pink500,
                onColor = White
            ),
            ColorItem(
                code = Green500Code,
                color = Green500,
                onColor = White
            ),
            ColorItem(
                code = Purple500Code,
                color = Purple500,
                onColor = White
            ),
            ColorItem(
                code = LightGreen500Code,
                color = LightGreen500,
                onColor = Dark
            ),
            ColorItem(
                code = DeepPurple500Code,
                color = DeepPurple500,
                onColor = White
            ),
            ColorItem(
                code = Lime500Code,
                color = Lime500,
                onColor = Dark
            ),
            ColorItem(
                code = Indigo500Code,
                color = Indigo500,
                onColor = White
            ),
            ColorItem(
                code = Yellow500Code,
                color = Yellow500,
                onColor = Dark
            ),
            ColorItem(
                code = Blue500Code,
                color = Blue500,
                onColor = White
            ),
            ColorItem(
                code = Amber500Code,
                color = Amber500,
                onColor = Dark
            ),
            ColorItem(
                code = SkyBlue500Code,
                color = SkyBlue500,
                onColor = White
            ),
            ColorItem(
                code = Orange500Code,
                color = Orange500,
                onColor = Dark
            ),
            ColorItem(
                code = Cyan500Code,
                color = Cyan500,
                onColor = White
            ),
            ColorItem(
                code = DeepOrange500Code,
                color = DeepOrange500,
                onColor = White
            ),
        )

        fun map(code: String) = colors.find { it.code == code } ?: colors[3]
    }
}