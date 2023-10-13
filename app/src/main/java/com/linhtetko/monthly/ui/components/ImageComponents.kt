package com.linhtetko.monthly.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun NetworkImage(modifier: Modifier = Modifier, data: Any) {
    AsyncImage(
        modifier = modifier,
        model = data,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
    )
}

@Preview
@Composable
fun NetworkImagePreview() {
    MonthlyTheme {
        NetworkImage(data = "")
    }
}