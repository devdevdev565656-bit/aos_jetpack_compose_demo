package app.aos.jp.demo.ui.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.aos.jp.demo.Greeting
import app.aos.jp.demo.nvaigation.AppNavigation
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.nvaigation.AppRoute
import app.aos.jp.demo.ui.components.CustomSearchWithHistory
import app.aos.jp.demo.ui.components.SearchBarWithHistory


@Composable
fun HomeScreen(appNavigator: AppNavigator) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(top= innerPadding.calculateTopPadding())) {
            CustomSearchWithHistory()
            BasicList(appNavigator)
        }
    }
}

@Composable
fun BasicList(appNavigator: AppNavigator) {
    val items = (1..50).map { "Item $it" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        itemsIndexed(items)  { index, item ->
            ListItem(
                modifier = Modifier.clickable {
                    appNavigator.navigate(AppRoute.Settings)
                },
                headlineContent = { Text(item) },
                supportingContent = { Text("Supporting text") }
            )
            if (index < items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            }

        }
    }
}