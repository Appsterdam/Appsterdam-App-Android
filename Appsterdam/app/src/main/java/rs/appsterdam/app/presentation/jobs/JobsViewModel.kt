package rs.appsterdam.app.presentation.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _flow: MutableStateFlow<List<Job>> = MutableStateFlow(value = listOf())
    val flow: StateFlow<List<Job>> = _flow

    private var _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
    val loading: MutableStateFlow<Boolean> = _loading

    private val eventChannel = Channel<String>()
    val eventFlow: Flow<String> = eventChannel.receiveAsFlow()

    fun fetchData() {
        viewModelScope.launch {
            repository.refreshJobs()
        }
        viewModelScope.launch {
            repository.getJobs().collect {
                _loading.value = false
                _flow.value = it
            }
            repository.error.collect {
                _loading.value = false
                eventChannel.send(element = it)
            }
        }
    }
}
