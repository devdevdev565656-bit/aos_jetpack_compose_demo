package app.aos.jp.demo.ui.feature.cathome.myaccount

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.ui.feature.cathome.CustomCatHomeViewPager
import app.aos.jp.demo.ui.theme.JPDemoTheme
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin



@Composable
fun MyAccountScreen() {

    FiveStarRadarChart()
}
@Composable
fun FiveStarRadarChart() {
    val radarLabels = listOf("速度", "耐力", "力量", "技巧", "敏捷")  // 5星維度
    val scores = listOf(80.0, 90.0, 70.0, 95.0, 85.0)  // 5個分數

    RadarChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        radarLabels = radarLabels,
        labelsStyle = TextStyle(
            color = Color(0xFF555555),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        netLinesStyle = NetLinesStyle(
            netLineColor = Color(0xFFE0E0E0),
            netLinesStrokeWidth = 2f,
            netLinesStrokeCap = StrokeCap.Butt
        ),
        scalarSteps = 5,        // 0,20,40,60,80,100 刻度
        scalarValue = 100.0,    // 滿分
        scalarValuesStyle = TextStyle(
            color = Color(0xFF999999),
            fontSize = 11.sp
        ),
        polygons = listOf(
            Polygon(
                values = scores,  // 每個值自動繪圓點
                style = PolygonStyle(
                    fillColor = Color(0xFF4CAF50),     // 填充色
                    fillColorAlpha = 0.3f,
                    borderColor = Color.White,         // 圓點邊框
                    borderColorAlpha = 1f,
                    borderStrokeWidth = 12f,           // 圓點大小（關鍵）
                    borderStrokeCap = StrokeCap.Round  // 圓形端點
                ),
                unit = "aaa"
            )
        )
    )
}






@Preview(showBackground = true)
@Composable
fun CatHomePreview() {
    JPDemoTheme {
        MyAccountScreen()
    }
}
