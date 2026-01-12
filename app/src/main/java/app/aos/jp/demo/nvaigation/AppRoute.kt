package app.aos.jp.demo.nvaigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable



sealed interface AppRoute : NavKey {
    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Settings : AppRoute

    @Serializable
    data object CatHome : AppRoute
}
