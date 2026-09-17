package rs.appsterdam.app.ui.screens.home

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

class HomeViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val markdown: String) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadHomeContent()
    }

    private fun loadHomeContent() = viewModelScope.launch {
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
            .collect { homeValue ->
                if (homeValue != null) {
                    state.value = State.Success(homeValue.home.toString())
                }
            }
    }
}
