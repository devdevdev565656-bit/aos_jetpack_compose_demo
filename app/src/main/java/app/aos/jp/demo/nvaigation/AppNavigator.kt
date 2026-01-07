package app.aos.jp.demo.nvaigation

import android.os.Build
import androidx.annotation.RequiresApi

class AppNavigator(
    private val navState: AppNavigationState
) {

    fun navigate(route: AppRoute) {
        navState.backStack.add(route)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun goBack() {
        if (navState.canGoBack()) {
            navState.backStack.removeLast()
        }
    }

    /**
     * 典型「登入後重設」範例：清空並只留新 root。
     * 比 backStack.replaceAll 更直觀。
     */
    fun replaceWithRoot(route: AppRoute) {
        navState.backStack.clear()
        navState.backStack.add(route)
    }

    /**
     * 若你真的想用 replaceAll：
     * 把每個 entry 都變成同一個 route，再視需要裁掉多餘的。
     */
    fun replaceAllWith(route: AppRoute) {
        navState.backStack.replaceAll { route }
        if (navState.backStack.size > 1) {
            navState.backStack.subList(1, navState.backStack.size).clear()
        }
    }
}