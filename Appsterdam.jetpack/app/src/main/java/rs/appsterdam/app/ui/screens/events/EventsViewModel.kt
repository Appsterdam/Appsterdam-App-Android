package rs.appsterdam.app.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import rs.appsterdam.app.data.NetworkRepository
import rs.appsterdam.app.models.EventGroup
import java.lang.reflect.Type


class EventsViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val eventList: List<EventGroup>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadHomeContent()
    }

    private fun loadHomeContent() = viewModelScope.launch {
        state.value = State.Loading

        networkRepository.fetchData("https://appsterdam.rs/api/events.json")
            .map { str ->
                try {
                    val listType: Type = object : TypeToken<ArrayList<EventGroup>>() {}.type
                    Gson().fromJson<List<EventGroup>>(str, listType)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
            }
            .flowOn(Dispatchers.Default)
            .collect { eventList ->
                state.value = State.Success(eventList)
            }
    }
}
