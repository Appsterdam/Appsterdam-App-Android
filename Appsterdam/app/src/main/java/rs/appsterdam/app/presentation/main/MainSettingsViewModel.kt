package rs.appsterdam.app.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rs.appsterdam.app.domain.datastore.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class MainSettingsViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    fun isJobsEnabled(): Flow<Boolean> = dataStoreManager.enableJobsFlow.map {
        it
    }
}
