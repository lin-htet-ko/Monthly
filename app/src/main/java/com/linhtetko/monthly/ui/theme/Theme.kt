package com.linhtetko.monthly.ui.theme

import android.app.Activity
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.ColorItem.Companion.Green500Code

private val ColorScheme: (code: String) -> Colors = {
    val item = ColorItem.map(it)
    val main = item.color
    val error = if (it.contains("red")) Dark else Red500
    val onComponent = item.onColor

    Colors(
        primary = onComponent,
        secondary = onComponent,
        primaryVariant = onComponent,
        secondaryVariant = onComponent,
        background = main,
        surface = main,
        error = error,
        onPrimary = onComponent,
        onSecondary = onComponent,
        onBackground = onComponent,
        onSurface = onComponent,
        onError = onComponent,
        isLight = false
    )
}

@Composable
fun MonthlyTheme(
    colorCode: String = Green500Code,
    content: @Composable () -> Unit
) {

    val colorScheme = ColorScheme(colorCode)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.decorView.setBackgroundColor(colorScheme.surface.toArgb())
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        shapes = Shape,
        content = content
    )
}