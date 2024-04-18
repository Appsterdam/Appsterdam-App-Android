package rs.appsterdam.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.appsterdam.app.ui.screens.about.AboutViewModel
import rs.appsterdam.app.ui.screens.home.HomeViewModel
import rs.appsterdam.app.ui.screens.events.EventsViewModel

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { AboutViewModel() }
    viewModel { EventsViewModel() }
}