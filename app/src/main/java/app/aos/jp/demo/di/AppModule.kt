package app.aos.jp.demo.di

import app.aos.jp.demo.nvaigation.AppNavigationState
import app.aos.jp.demo.nvaigation.AppNavigator
import app.aos.jp.demo.nvaigation.rememberAppNavigationState
import app.aos.jp.demo.ui.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*
val appModule = module {
    single { UserRepository() }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}*/
val appModule = module {
    viewModelOf(::HomeViewModel)

    factory { (navState: AppNavigationState) ->
        AppNavigator(navState)
    }

}