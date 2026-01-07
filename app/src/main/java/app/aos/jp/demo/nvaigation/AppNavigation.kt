package app.aos.jp.demo.nvaigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider

import androidx.navigation3.ui.NavDisplay

import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import app.aos.jp.demo.ui.feature.home.HomeScreen
import app.aos.jp.demo.ui.feature.splash.AppSplashScreen


@Composable
fun rememberAppNavigationState(
    startRoute: AppRoute = AppRoute.Splash
): Pair<AppNavigationState, AppNavigator> {
    // 可用 rememberSerializable 包起來做狀態持久化，這裡先簡化
    val navState = remember { AppNavigationState(startRoute) }
    val navigator = remember { AppNavigator(navState) }
    return navState to navigator
}
@Composable
fun AppNavigation(windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass) {

    val (navState, navigator) = rememberAppNavigationState()

    NavDisplay(
        backStack = navState.backStack,
        onBack = { navState.backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),      // Saveable 狀態
            rememberViewModelStoreNavEntryDecorator()   // ViewModel 支援（關鍵！）
        ),
        entryProvider = entryProvider {
            entry<AppRoute.Splash>(metadata = NavDisplay.transitionSpec {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(1000)
                ) togetherWith ExitTransition.KeepUntilTransitionsFinished
            }
            ) {
                AppSplashScreen(navigator)
            }
            entry<AppRoute.Home> { key ->  // key 直接取得參數
                HomeScreen()
            }
        }
    )
}


/*
fun AppNavigation.navigateToHomeAsRoot() {
    backStack.replaceAll { HomeKey }   // 不在乎舊值，全部換成 HomeKey
    if (backStack.size > 1) {
        // 若你希望 stack 只剩一個，可再約束長度
        backStack.subList(1, backStack.size).clear()
    }
}*/
