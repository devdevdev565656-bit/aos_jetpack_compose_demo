package app.aos.jp.demo.nvaigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class AppNavigationState(
    startRoute: AppRoute
) {
    // 單 back stack 範例：SnapshotStateList
    val backStack: SnapshotStateList<AppRoute> =
        mutableStateListOf(startRoute)

    val current: AppRoute
        get() = backStack.last()

    fun canGoBack(): Boolean = backStack.size > 1
}