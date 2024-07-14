package rs.appsterdam.app.ui.screens.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import rs.appsterdam.app.models.Jobs
import java.lang.reflect.Type
import java.net.URL


class JobsViewModel: ViewModel() {
    sealed class State {
        object Loading : State()
        data class Success(val jobsList: List<Jobs>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadJobsContent()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadJobsContent() = viewModelScope.launch {
        state.value = State.Loading

        GlobalScope.launch(Dispatchers.IO) {
            val str = URL("https://appsterdam.rs/api/jobs.json").readText()
            val listType: Type = object : TypeToken<ArrayList<Jobs>>() {}.type
            val jobsList: List<Jobs> = Gson().fromJson(str, listType)

            GlobalScope.launch(Dispatchers.Main) {
                state.value = State.Success(jobsList)
            }
        }

    }
}