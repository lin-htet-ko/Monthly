package com.linhtetko.monthly.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.linhtetko.monthly.R

@Composable
fun Spacer1x() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_general)))
}
@Composable
fun Spacer2x() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2x)))
}

@Composable
fun HorizontalSpacer2x() {
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_2x)))
}

@Composable
fun HorizontalSpacer1x() {
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_general)))
}