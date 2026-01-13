package app.aos.jp.demo.ui.components.myaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aos.jp.demo.ui.feature.cathome.myaccount.MyAccountScreen
import app.aos.jp.demo.ui.theme.JPDemoTheme
import com.kevinnzou.compose.progressindicator.SimpleProgressIndicatorWithAnim


@Composable
fun LevelProgressBar() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Gray), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        SimpleProgressIndicatorWithAnim(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(4.dp),
            0.7f,
            cornerRadius = 35.dp,
            thumbRadius = 0.dp,
            thumbOffset = 1.5.dp
        )
    }

}



@Preview(showBackground = true)
@Composable
fun LevelProgressBarPreview() {
    JPDemoTheme {
        LevelProgressBar()
    }
}