package app.aos.jp.demo.ui.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun CustomSearchBar() {
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
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                    onValueChange = {
                        text = it
                        if (text.length > 0) {
                            expanded = true
                        } else {
                            expanded = false
                        }
                        Log.d("testing123", "expanded:${expanded}")
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // 輸入完成 -> 取消焦點、收鍵盤
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)

                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            when {
                                focusState.isFocused -> {
                                    expanded = true
                                    println("BasicTextField 獲得焦點")
                                    // 可執行：顯示提示、開啟動畫等
                                }

                                focusState.hasFocus -> {
                                    // hasFocus 是即將獲得焦點的過渡狀態（較少用）
                                }

                                else -> {
                                    println("BasicTextField 失去焦點")
                                    expanded = false
                                    // 可執行：驗證文字、隱藏鍵盤（若需要強制）等
                                }
                            }

                        }


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
                    .fillMaxHeight() // 蓋住剩餘畫面
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        expanded = false
                        focusManager.clearFocus()
                    }
            )
        }
    }
}