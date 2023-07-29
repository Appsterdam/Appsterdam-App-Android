package rs.appsterdam.app.ui.screens.home

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
import java.net.URL

class HomeViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val markdown: String) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadHomeContent()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadHomeContent() = viewModelScope.launch {
        state.value = State.Loading

        GlobalScope.launch(Dispatchers.IO) {
            val str = URL("https://appsterdam.rs/api/app.json").readText()
            val json = JSONObject(str)

            val gson = Gson()
            val homeValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            println("APP:Home=$homeValue")
            GlobalScope.launch(Dispatchers.Main) {
                state.value = State.Success(homeValue.home.toString())
            }
        }

    }
}
