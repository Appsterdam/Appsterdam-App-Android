package rs.appsterdam.app.domain.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

open class DataStoreManager(private val context: Context) {

    val showDescriptionsFlow: Flow<Boolean> = context.dataStore.data.map {
        it[KEY_SHOW_DESCRIPTIONS] ?: true
    }
    val enableSearchFlow: Flow<Boolean> = context.dataStore.data.map {
        it[KEY_ENABLE_SEARCH] ?: true
    }
    val notifyOnNewEventsFlow: Flow<Boolean> = context.dataStore.data.map {
        it[KEY_NOTIFY_ON_NEW_EVENTS] ?: false
    }
    val enableJobsFlow: Flow<Boolean> = context.dataStore.data.map {
        it[KEY_ENABLE_JOBS] ?: true
    }
    val notifyOnNewJobsFlow: Flow<Boolean> = context.dataStore.data.map {
        it[KEY_NOTIFY_ON_NEW_JOBS] ?: false
    }

    suspend fun saveShowDescriptions(value: Boolean) {
        saveSettings(key = KEY_SHOW_DESCRIPTIONS, value = value)
    }

    suspend fun saveEnableSearch(value: Boolean) {
        saveSettings(key = KEY_ENABLE_SEARCH, value = value)
    }

    suspend fun saveNotifyOnNewEvents(value: Boolean) {
        saveSettings(key = KEY_NOTIFY_ON_NEW_EVENTS, value = value)
    }

    suspend fun saveEnableJobs(value: Boolean) {
        saveSettings(key = KEY_ENABLE_JOBS, value = value)
    }

    suspend fun saveNotifyOnNewJobs(value: Boolean) {
        saveSettings(key = KEY_NOTIFY_ON_NEW_JOBS, value = value)
    }

    private suspend fun saveSettings(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    companion object {

        val KEY_SHOW_DESCRIPTIONS: Preferences.Key<Boolean> =
            booleanPreferencesKey(name = "show_descriptions")
        val KEY_ENABLE_SEARCH: Preferences.Key<Boolean> =
            booleanPreferencesKey(name = "enable_search")
        val KEY_NOTIFY_ON_NEW_EVENTS: Preferences.Key<Boolean> =
            booleanPreferencesKey(name = "notify_on_new_events")
        val KEY_ENABLE_JOBS: Preferences.Key<Boolean> =
            booleanPreferencesKey(name = "enable_jobs")
        val KEY_NOTIFY_ON_NEW_JOBS: Preferences.Key<Boolean> =
            booleanPreferencesKey(name = "notify_on_new_jobs")
    }
}
