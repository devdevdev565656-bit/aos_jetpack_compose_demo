package app.aos.jp.demo.ui.feature.cathome

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class CatHomeTab(val title: String, val icon: ImageVector) {
    ACCOUNT_BOX("AccountBox", Icons.Default.AccountBox),
    Reward("Reward", Icons.Default.Star),
}