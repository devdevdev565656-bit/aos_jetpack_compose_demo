package app.aos.jp.demo.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithHistory() {
    // 搜尋文字
    var query by remember { mutableStateOf("") }

    // 是否正在顯示搜尋模式（點擊後展開）
    var active by remember { mutableStateOf(false) }

    // 最近搜尋紀錄（範例資料，可換成 ViewModel 或 SharedPreferences 儲存）
    val searchHistory = remember {
        mutableStateListOf(
            "Jetpack Compose",
            "Material 3",
            "Android SearchView",
            "Kotlin Coroutines",
            "Room Database"
        )
    }

    val focusRequester = remember { FocusRequester() }

    // 使用 Material 3 的 SearchBar（推薦方式）
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { searchText ->
            // 執行搜尋時，將文字加入紀錄（避免重複）
      /*      if (searchText.isNotBlank() && searchText !in searchHistory) {
                searchHistory.add(0, searchText)  // 新增到最上面
                if (searchHistory.size > 10) searchHistory.removeAt(searchHistory.lastIndex)
            }*/
            active = false  // 搜尋完成後可選擇關閉
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("搜尋...") },
        leadingIcon = {
            // 永遠顯示搜尋圖示
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            Row {
                // 清除輸入文字按鈕
                if (active && query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear text"
                        )
                    }
                }
                // 返回按鈕（只有在 active 時顯示）
        /*        if (active) {
                    IconButton(onClick = { active = false }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }*/
            }
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
    ) {
        // 這裡是 active = true 時下方顯示的內容（搜尋建議或歷史紀錄）
   /*     if (searchHistory.isEmpty()) {
            ListItem(
                headlineContent = { Text("無搜尋紀錄", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            )
        } else {
            LazyColumn {
                items(searchHistory) { historyItem ->
                    ListItem(
                        headlineContent = { Text(historyItem) },
                        leadingContent = {
                            Icon(
                                Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                            .clickable {
                                query = historyItem          // 填入搜尋欄
                                active = true                // 保持展開狀態
                                focusRequester.requestFocus() // 聚焦到輸入框
                                // 如果想直接搜尋，可呼叫 onSearch(historyItem)
                            }
                    )
                    Divider()
                }

                // 可選：清除全部紀錄的按鈕
                item {
                    ListItem(
                        headlineContent = {
                            Text(
                                "清除搜尋紀錄",
                                color = Color.Red,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        modifier = Modifier.clickable {
                            searchHistory.clear()
                        }
                    )
                }
            }
        }*/
    }
}

@Composable
fun CustomSearchWithHistory() {
    // 搜尋文字
    var text by remember { mutableStateOf("") }

    // 是否展開顯示歷史紀錄
    var expanded by remember { mutableStateOf(false) }

    // 最近搜尋紀錄
    val searchHistory = remember {
        mutableStateListOf(
            "Jetpack Compose",
            "Custom Search Bar",
            "Kotlin Android",
            "Material Design",
            "Compose Layout"
        )
    }

    val focusRequester = remember { FocusRequester() }

    Column {
        // 自訂搜尋欄
        Surface(
            shape = RoundedCornerShape(24.dp),
            shadowElevation = if (expanded) 8.dp else 4.dp,
            tonalElevation = if (expanded) 4.dp else 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(IntrinsicSize.Min)
            ) {
                // 永遠顯示的搜尋圖示
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(12.dp))

                // 文字輸入區
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                "搜尋...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .clickable { expanded = true } // 點擊空白處也展開
                )

                // 清除按鈕
                AnimatedVisibility(visible = text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // 展開時顯示的歷史紀錄列表（帶動畫）
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Surface(
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
            ) {
                if (searchHistory.isEmpty()) {
                    ListItem(
                        headlineContent = {
                            Text(
                                "無搜尋紀錄",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                } else {
                    LazyColumn {
                        items(searchHistory) { item ->
                            ListItem(
                                headlineContent = { Text(item) },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.MailOutline,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                trailingContent = {
                                    IconButton(onClick = { searchHistory.remove(item) }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                modifier = Modifier.clickable {
                                    text = item
                                    focusRequester.requestFocus()
                                    // expanded = false // 可選擇點擊後收合
                                }
                            )
                            Divider()
                        }

                        item {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "清除所有搜尋紀錄",
                                        color = Color.Red,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                modifier = Modifier.clickable {
                                    searchHistory.clear()
                                }
                            )
                        }
                    }
                }
            }
        }

        // 點擊外部收合（可選）
        if (expanded) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1000.dp) // 蓋住剩餘畫面
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                        expanded = false
                    }
            )
        }
    }
}