package app.aos.jp.demo.ui.feature.cathome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.aos.jp.demo.nvaigation.AppNavigator



@Composable
fun CatHomeWebScreen(appNavigator: AppNavigator) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFFf29f3a)).padding(top = innerPadding.calculateTopPadding())) {
            CustomCatHomeViewPager()
        }

    }
}