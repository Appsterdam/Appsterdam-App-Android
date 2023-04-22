package rs.appsterdam.app.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import rs.appsterdam.app.models.EventGroup
import java.lang.reflect.Type
import java.net.URL


class EventsViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val eventList: List<EventGroup>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadHomeContent()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadHomeContent() = viewModelScope.launch {
        state.value = State.Loading

        GlobalScope.launch(Dispatchers.IO) {
            val str = URL("https://appsterdam.rs/api/events_100.json").readText()
            val listType: Type = object : TypeToken<ArrayList<EventGroup>>() {}.type
            val eventList: List<EventGroup> = Gson().fromJson(str, listType)

            GlobalScope.launch(Dispatchers.Main) {
                state.value = State.Success(eventList)
            }
        }

    }
}
