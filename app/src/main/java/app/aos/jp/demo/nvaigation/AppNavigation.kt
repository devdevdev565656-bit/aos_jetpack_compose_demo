package app.aos.jp.demo.nvaigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider

import androidx.navigation3.ui.NavDisplay

import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.SinglePaneSceneStrategy
import app.aos.jp.demo.ui.feature.cathome.CatHomeScreen
import app.aos.jp.demo.ui.feature.home.HomeScreen
import app.aos.jp.demo.ui.feature.setting.SettingScreen
import app.aos.jp.demo.ui.feature.splash.AppSplashScreen
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf


@Composable
fun rememberAppNavigationState(
    startRoute: AppRoute = AppRoute.Splash
): Pair<AppNavigationState, AppNavigator> {
    // 可用 rememberSerializable 包起來做狀態持久化，這裡先簡化
 /*   val navState = remember { AppNavigationState(startRoute) }
    val navigator = remember { AppNavigator(navState) }*/
    // 1. 產生 State (UI 層負責生命週期與保存)
    val navState = rememberSaveable(saver = AppNavigationState.Saver) {
        AppNavigationState(startRoute)
    }

    // 2. 注入 Navigator (Koin 負責依賴建構)
    // 使用 parametersOf 將 navState 傳給 Koin module 裡的 factory
    val navigator: AppNavigator = koinInject { parametersOf(navState) }
    return navState to navigator
}
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppNavigation(windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass) {

    val (navState, navigator) = rememberAppNavigationState()

    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val singlePaneStrategy = remember { SinglePaneSceneStrategy<NavKey>() }

    val metadataMap: MutableMap<String, Any> = mutableMapOf()
/*    metadataMap += NavDisplay.transitionSpec {
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(1000)
        ) togetherWith ExitTransition.KeepUntilTransitionsFinished
    }*/
    metadataMap += ListDetailSceneStrategy.listPane(
        detailPlaceholder = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("选择一个对话")
            }
        }
    )
    NavDisplay(
        backStack = navState.backStack,
        onBack = { navState.backStack.removeLastOrNull() },
        sceneStrategy = listDetailStrategy then singlePaneStrategy,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),      // Saveable 狀態
            rememberViewModelStoreNavEntryDecorator()   // ViewModel 支援（關鍵！）
        ),
        entryProvider = entryProvider {
            entry<AppRoute.Splash>(
                metadata = NavDisplay.transitionSpec {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(1000)
                ) togetherWith ExitTransition.KeepUntilTransitionsFinished
            },
            ) {
                AppSplashScreen(navigator)
            }
            entry<AppRoute.Home>(metadata = metadataMap) { key ->  // key 直接取得參數
                HomeScreen(navigator)
            }

            entry<AppRoute.Settings>() { key ->  // key 直接取得參數
                SettingScreen(navigator)
            }
            entry<AppRoute.CatHome>() {
                CatHomeScreen(navigator)
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
