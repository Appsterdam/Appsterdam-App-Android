package rs.appsterdam.app.presentation.events

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rs.appsterdam.app.domain.datastore.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class EventsSettingsViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    fun isSearchEnabled(): Flow<Boolean> = dataStoreManager.enableSearchFlow.map {
        it
    }

    fun showDescriptions(): Flow<Boolean> = dataStoreManager.showDescriptionsFlow.map {
        it
    }
}
