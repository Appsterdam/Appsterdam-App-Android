package rs.appsterdam.app.ui.screens.jobs

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
import rs.appsterdam.app.models.Jobs
import java.lang.reflect.Type


class JobsViewModel(private val networkRepository: NetworkRepository): ViewModel() {
    sealed class State {
        object Loading : State()
        data class Success(val jobsList: List<Jobs>) : State()
    }

    val state = MutableStateFlow<State>(State.Loading)

    init {
        loadJobsContent()
    }

    private fun loadJobsContent() = viewModelScope.launch {
        state.value = State.Loading

        networkRepository.fetchData("https://appsterdam.rs/api/jobs.json")
            .map { str ->
                try {
                    val listType: Type = object : TypeToken<ArrayList<Jobs>>() {}.type
                    Gson().fromJson<List<Jobs>>(str, listType)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
            }
            .flowOn(Dispatchers.Default)
            .collect { jobsList ->
                state.value = State.Success(jobsList)
            }
    }
}