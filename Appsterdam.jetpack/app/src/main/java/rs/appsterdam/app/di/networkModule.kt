package rs.appsterdam.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rs.appsterdam.app.data.NetworkRepository

val networkModule = module {
    single { NetworkRepository(androidContext()) }
}
