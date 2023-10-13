package com.linhtetko.monthly.ui.components

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, label: String, onTap: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onTap,
        elevation = ButtonDefaults.elevation(defaultElevation = dimensionResource(id = R.dimen.size_empty)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.88f))
    ) {
        Text(
            text = label,
            fontWeight = MaterialTheme.typography.h1.fontWeight,
            color = MaterialTheme.colors.background
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    MonthlyTheme {
        PrimaryButton(
            label = "Click me quick",
            onTap = {}
        )
    }
}