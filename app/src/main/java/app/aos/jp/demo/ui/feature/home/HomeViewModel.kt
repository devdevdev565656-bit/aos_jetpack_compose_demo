package app.aos.jp.demo.ui.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow("Initial State")
    val uiState: StateFlow<String> = _uiState.asStateFlow()
}