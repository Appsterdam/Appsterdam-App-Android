package rs.appsterdam.app.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _flow: MutableStateFlow<List<Category>> = MutableStateFlow(value = listOf())
    val flow: StateFlow<List<Category>> = _flow

    private var _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
    val loading: MutableStateFlow<Boolean> = _loading

    private val eventChannel = Channel<String>()
    val eventFlow: Flow<String> = eventChannel.receiveAsFlow()

    fun fetchData() {
        viewModelScope.launch {
            repository.refreshCategories()
        }
        viewModelScope.launch {
            repository.getCategories().collect {
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
