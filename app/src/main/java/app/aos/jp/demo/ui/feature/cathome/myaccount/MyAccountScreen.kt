package app.aos.jp.demo.ui.feature.cathome.myaccount


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aos.jp.demo.ui.theme.JPDemoTheme
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLinkStyles
import androidx.constraintlayout.compose.ConstrainedLayoutReference

import androidx.constraintlayout.compose.ConstraintLayout
import app.aos.jp.demo.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun MyAccountScreen() {
    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("lottie_pen.json")
    )

    // Optional: control playback, speed, etc.
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,   // or a specific number
        // isPlaying = true,                            // can be controlled by state
        // speed = 1.5f                                 // example: 1.5× faster
    )
    CompositionLocalProvider(LocalOverscrollFactory provides null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            FiveStarRadarChart5CirclesAligned()

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(5.dp)
            ) {
                val (imgAlbum, imgEdit, lottiePen, tvLevel) = createRefs()

                GlideImage(
                    model = R.drawable.album,  // Direct drawable ID
                    contentDescription = "App icon",
                    modifier = Modifier
                        .size(136.dp)
                        .constrainAs(imgAlbum) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                GlideImage(
                    model = R.drawable.edit,  // Direct drawable ID
                    contentDescription = "App icon",
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(imgEdit) {
                            bottom.linkTo(imgAlbum.bottom)
                            start.linkTo(imgAlbum.end, margin = -15.dp)
                        }
                )
                LottieAnimation(
                    composition = composition,
                    progress = { progress },           // ← binds animated value
                    modifier = Modifier
                        .size(30.dp)
                        .constrainAs(lottiePen) {
                            top.linkTo(imgAlbum.top)
                            start.linkTo(imgAlbum.end, margin = -15.dp)
                        }
                )
                Column(
                    modifier = Modifier.constrainAs(tvLevel) {
                        bottom.linkTo(imgAlbum.bottom)
                        start.linkTo(imgAlbum.end, margin = 10.dp)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "Lv.30",
                        fontWeight = FontWeight.Bold,          // makes it bold
                        fontSize = 20.sp,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Blue,
                                shape = RoundedCornerShape(16.dp)  // rounded corners (adjust dp as needed)
                            )
                            .padding(5.dp)
                            .onGloballyPositioned { coordinates ->
                                textWidth = with(density) { coordinates.size.width.toDp() }
                            }
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .width(textWidth),
                        thickness = 0.5.dp,           // line thickness
                        color = Color.Black     // optional: default is onSurfaceVariant
                    )

                    Text(
                        "LEVEL",
                        fontSize = 20.sp,
                    )
                }
            }


        }
    }

}


@Composable
fun FiveStarRadarChart5CirclesAligned() {
    val radarLabels = listOf("速度", "耐力", "力量", "技巧", "敏捷")
    val scores = listOf(80.0, 90.0, 70.0, 95.0, 100.0)

    val labelsStyle = TextStyle(
        color = Color(0xFF333333),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )

    val scalarValuesStyle = TextStyle(color = Color.Gray, fontSize = 10.sp)
    RadarChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        radarLabels = radarLabels,
        labelsStyle = labelsStyle,
        netLinesStyle = NetLinesStyle(
            netLineColor = Color(0xFFF5F5F5),    // 超淺網格
            netLinesStrokeWidth = 1.2f,         // 超細線
            netLinesStrokeCap = StrokeCap.Round
        ),
        scalarSteps = 2,  // ✅ 5圈 (0,20,40,60,80,100)
        scalarValue = 100.0,
        scalarValuesStyle = scalarValuesStyle,
        polygons = listOf(
            Polygon(
                values = scores,
                unit = "",
                style = PolygonStyle(
                    fillColor = Color(0xFF4CAF50),
                    fillColorAlpha = 0.28f,
                    borderColor = Color.Black,
                    borderColorAlpha = 1f,
                    borderStrokeWidth = 22f,            // 延伸到第5圈
                    borderStrokeCap = StrokeCap.Round
                )
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
