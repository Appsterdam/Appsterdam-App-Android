package rs.appsterdam.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.appsterdam.app.ui.screens.about.AboutViewModel
import rs.appsterdam.app.ui.screens.home.HomeViewModel

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { AboutViewModel() }
}