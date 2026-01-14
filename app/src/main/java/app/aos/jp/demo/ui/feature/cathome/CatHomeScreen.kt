package app.aos.jp.demo.ui.feature.cathome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.ui.feature.cathome.myaccount.MyAccountScreen
import app.aos.jp.demo.ui.theme.JPDemoTheme
import kotlinx.coroutines.launch

@Composable
fun CatHomeScreen(appNavigator: AppNavigator) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFFf29f3a)).padding(top = innerPadding.calculateTopPadding())) {
            CustomCatHomeViewPager()
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCatHomeViewPager() {
    val tabs = CatHomeTab.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val tabWidths = remember { mutableStateMapOf<Int, Dp>() }
    var currentSheetExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        SecondaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFFf29f3a),
            contentColor = Color.White,
            // 1. Change the Tab Bar Height here (e.g., 40.dp for a slim look)
            modifier = Modifier.height(44.dp),
            edgePadding = 0.dp,
            indicator = {
                Box(
                    Modifier
                        .tabIndicatorOffset(pagerState.currentPage, matchContentSize = false)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomCenter)
                ) {
                    val currentWidth = tabWidths[pagerState.currentPage] ?: 0.dp
                    Box(
                        modifier = Modifier
                            .width(currentWidth)
                            .height(4.dp)
                            // 1. Clip the shape or use background(color, shape)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp))
                            .background(Color.White)
                    )
                }
            },

            divider = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    // 2. Ensure the inner container fills the custom height
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 3.dp) // Gap to indicator
                            .onSizeChanged { size ->
                                tabWidths[index] = with(density) { size.width.toDp() }
                            },
                        verticalAlignment = Alignment.Bottom, // Align items to the bottom
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val iconId = "icon"  // 只是個識別符，隨便取
                        // 建立帶有內嵌圖示的文字
                        val annotatedString = buildAnnotatedString {
                            // 先插入 Icon 的位置
                            appendInlineContent(iconId, "[圖示]")

                            append("${tab.title}")

                        }

                        val inlineContent = mapOf(
                            iconId to InlineTextContent(
                                placeholder = Placeholder(
                                    width = 24.sp,           // Icon 寬度
                                    height = 24.sp,          // Icon 高度
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Bottom  // 垂直對齊方式
                                    // 改成 PlaceholderVerticalAlign.Top 就會貼齊文字最頂端
                                ),
                                children = {
                                    Icon(tab.icon, contentDescription = null, modifier = Modifier.size(24.dp))
                                }
                            )
                        )
                       // Icon(tab.icon, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(text =annotatedString, fontSize = 24.sp,inlineContent = inlineContent)  // 關鍵！把 Icon 綁進去)
                    }
                }
            }
        }

        HorizontalPager(state = pagerState, userScrollEnabled = !currentSheetExpanded, modifier = Modifier.weight(1f)) { page ->
            if (page == 0) {
                PageWithBottomSheet(
                    pageNumber = 1,
                    onSheetStateChanged = { isExpanded ->
                        currentSheetExpanded = isExpanded
                    }
                )
                //MyAccountScreen()
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Page: ${tabs[page].title}")
                }
            }

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageWithBottomSheet(
    pageNumber: Int,
    onSheetStateChanged: (Boolean) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    // 監控 Sheet 狀態並回報給父層
    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
        onSheetStateChanged(isExpanded)
    }
    val scope = rememberCoroutineScope() // <--- Get Scope

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize().background(Color.Transparent),
        sheetPeekHeight = 80.dp,

        sheetContentColor = Color.Transparent,
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Text(
                    "Page $pageNumber 的獨立 Sheet",
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("這個 Sheet 只屬於第 $pageNumber 頁")
                Spacer(modifier = Modifier.height(16.dp))

                // 提供手動展開/收合的按鈕
                Button(onClick = {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                            scaffoldState.bottomSheetState.partialExpand()
                        } else {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                }) {
                    Text("切換展開/收合")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    when (pageNumber) {
                        0 -> Color(0xFFE1BEE7)
                        1 -> Color.Transparent
                        else -> Color(0xFFB2DFDB)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Page $pageNumber Content",
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("向上拉動底部面板查看更多")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatHomePreview() {
    JPDemoTheme {
        CustomCatHomeViewPager()
    }
}

