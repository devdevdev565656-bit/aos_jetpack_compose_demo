package app.aos.jp.demo.other.navigation3scenestask

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey

import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.navigation3.scene.SinglePaneSceneStrategy
import androidx.navigation3.ui.NavDisplay

@kotlinx.serialization.Serializable
object TodosHomeKey : NavKey  // 清單頁



sealed interface AppRoute2 : NavKey {
    @Serializable
    data object Splash : AppRoute2

    @Serializable
    data object Home : AppRoute2

    @Serializable
    data object Detail : AppRoute2
}

class ListDetailScene<T : Any>(
    override val key: Any,
    private val listEntry: NavEntry<T>,
    private val detailEntry: NavEntry<T>?,
    override val entries: List<NavEntry<T>>,
    override val previousEntries: List<NavEntry<T>>,
    override val content: @Composable (() -> Unit)
) : Scene<T> {

    @Composable
    fun Content() {

        Row(Modifier.fillMaxSize()) {
            // 左側列表
            Box(Modifier.weight(1f).fillMaxHeight()) {
                listEntry.Content()
            }
            // 右側詳情（可為 null）
            if (detailEntry != null) {
                Box(Modifier.weight(2f).fillMaxHeight()) {
                    detailEntry.Content()
                }
            }
        }
    }
}

class ListDetailSceneStrategy<T : Any>(
    private val windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass
) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(
        entries: List<NavEntry<T>>
    ): Scene<T>? {
        // 小螢幕直接放棄，由下一個策略處理（例如 SinglePane）
        Log.d("testing123","windowSizeClass:${windowSizeClass.widthSizeClass}, " +
                "Medium:${WindowWidthSizeClass.Medium},  Expanded:${WindowWidthSizeClass.Expanded}")
        if (windowSizeClass.widthSizeClass < WindowWidthSizeClass.Medium) {
            return null
        }
        if (entries.isEmpty()) return null

        // 找到最後一個 Home 以及最後一個 Detail
        val listEntry = entries.lastOrNull { it.contentKey is AppRoute2.Home } ?: return null
        val detailEntry = entries.lastOrNull { it.contentKey is AppRoute2.Detail }

        // 若只有列表，也允許只顯示左欄
        val sceneKey = (detailEntry?.contentKey ?: listEntry.contentKey)

        return ListDetailScene(
            key = sceneKey,
            listEntry = listEntry,
            detailEntry = detailEntry,
            entries = entries,
            content = { Content(listEntry as NavEntry<AppRoute2>,
                detailEntry as NavEntry<AppRoute2>
            ) },
            previousEntries = if (detailEntry != null) {
                entries.dropLast(1)
            } else {
                entries.dropLast(0)
            }
        )
    }
}

@Composable
fun Content( listEntry: NavEntry<AppRoute2>,detailEntry: NavEntry<AppRoute2>) {

     Row(Modifier.fillMaxSize()) {
         Row(Modifier.fillMaxSize()) {
             // 左側列表
             Box(Modifier.weight(1f).fillMaxHeight()) {
                 listEntry.Content()
             }
             // 右側詳情（可為 null）

                 Box(Modifier.weight(2f).fillMaxHeight()) {
                     detailEntry.Content()
                 }

         }

      }
}

@Composable
fun HomeScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Home (List)")
        Spacer(Modifier.height(8.dp))

        // 假資料列表
        (1L..10L).forEach { id ->
            Text(
                text = "Item $id",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      //  backStack.push(NavKey.Detail(id))
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DetailScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detail for id=${1}")
        Spacer(Modifier.height(8.dp))
        Button(onClick = { }) {
            Text("Back")
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AppRoot(windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass) {
    //val backStack = rememberNavBackStack<NavKey>(startDestination = AppRoute2.Home)
    val backStack: SnapshotStateList<AppRoute2> =
        mutableStateListOf(AppRoute2.Home)
    val listDetailStrategy = remember {
        ListDetailSceneStrategy<NavKey>(windowSizeClass)
    }
    val singlePaneStrategy = remember { SinglePaneSceneStrategy<NavKey>() }

    NavDisplay(
        backStack = backStack,
        // 先試 List–Detail，失敗就退回單頁
        sceneStrategy = listDetailStrategy then singlePaneStrategy,
        entryProvider = entryProvider {
            entry<AppRoute2.Home> {
                HomeScreen()
            }
            entry<AppRoute2.Detail> { key ->
                DetailScreen()
            }
        }
    )
}