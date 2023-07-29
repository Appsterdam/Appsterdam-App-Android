package rs.appsterdam.app.ui.screens.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import rs.appsterdam.app.models.Appsterdamer
import rs.appsterdam.app.models.Team
import java.net.URL

class AboutViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val teams: List<Team>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadAboutContent()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadAboutContent() = viewModelScope.launch {
        state.value = State.Loading

        GlobalScope.launch(Dispatchers.IO) {
            val str = URL("https://appsterdam.rs/api/app.json").readText()
            val json = JSONObject(str)

            val gson = Gson()
            val aboutValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            println("APP:About=${aboutValue.teams}")

            GlobalScope.launch(Dispatchers.Main) {
                state.value = State.Success(aboutValue.teams)
            }
        }
    }
}