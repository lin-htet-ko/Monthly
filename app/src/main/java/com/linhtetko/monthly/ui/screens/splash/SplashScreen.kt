package com.linhtetko.monthly.ui.screens.splash

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController, nextRoute: String) {

    var logoSize by remember {
        mutableStateOf(0.dp)
    }
    val logoOffsetAnim by animateIntOffsetAsState(
        targetValue = IntOffset(
            0,
            logoSize.value.toInt() - 200
        ), label = "Animate Logo"
    )


    LaunchedEffect(key1 = nextRoute) {
        for (time in 0..200) {
            delay(1)
            logoSize = time.dp
        }
        launch {
            delay(500)
            navController.navigate(nextRoute) {
                popUpTo(navController.graph.id) {
                    saveState = false
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_logo_for_splash),
            contentDescription = null,
            modifier = Modifier
                .size(logoSize)
                .offset {
                    logoOffsetAnim
                }
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MonthlyTheme {
        SplashScreen(
            navController = rememberNavController(),
            nextRoute = ""
        )
    }
}