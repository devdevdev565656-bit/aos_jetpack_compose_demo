package app.aos.jp.demo.ui.feature.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import app.aos.jp.demo.nvaigation.AppNavigationState
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.nvaigation.AppRoute


@Composable
fun AppSplashScreen(appNavigator: AppNavigator) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000) // Simulate loading (e.g., auth check)
        appNavigator.replaceAllWith(AppRoute.Home)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary))),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "My App",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.scale(scale)
        )
    }
}