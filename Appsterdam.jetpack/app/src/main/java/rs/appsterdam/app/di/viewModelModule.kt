package rs.appsterdam.app.di

import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.appsterdam.app.ui.screens.about.AboutViewModel
import rs.appsterdam.app.ui.screens.home.HomeViewModel
import rs.appsterdam.app.ui.screens.events.EventsViewModel
import org.koin.core.module.dsl.*
import org.koin.androidx.compose.getViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AboutViewModel)
    viewModelOf(::EventsViewModel)
}