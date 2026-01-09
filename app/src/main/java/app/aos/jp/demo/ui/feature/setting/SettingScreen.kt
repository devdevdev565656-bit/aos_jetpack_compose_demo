package app.aos.jp.demo.ui.feature.setting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.ui.feature.home.BasicList

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SettingScreen(appNavigator: AppNavigator) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(verticalArrangement = Arrangement.Center) {
            Text("setting screen")
            Button(onClick = {
                appNavigator.goBack()
            }) {
                Text("back")
            }
        }
    }
}