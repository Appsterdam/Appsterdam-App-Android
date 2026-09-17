package rs.appsterdam.app.ui.screens.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import rs.appsterdam.app.data.NetworkRepository
import rs.appsterdam.app.models.Appsterdamer
import rs.appsterdam.app.models.Team

class AboutViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val teams: List<Team>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadAboutContent()
    }

    private fun loadAboutContent() = viewModelScope.launch {
        state.value = State.Loading

        networkRepository.fetchData("https://appsterdam.rs/api/app.json")
            .map { str ->
                try {
                    val json = JSONObject(str)
                    val gson = Gson()
                    gson.fromJson(json.toString(), Appsterdamer::class.java)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            .flowOn(Dispatchers.Default)
            .collect { aboutValue ->
                if (aboutValue != null) {
                    println("APP:About=${aboutValue.teams}")
                    state.value = State.Success(aboutValue.teams)
                }
            }
    }
}