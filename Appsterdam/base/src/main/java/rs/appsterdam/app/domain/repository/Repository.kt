package rs.appsterdam.app.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import rs.appsterdam.app.data.db.AppsterdamDatabase
import rs.appsterdam.app.data.model.App
import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.data.model.Team
import rs.appsterdam.app.data.network.AppsterdamService
import javax.inject.Inject

open class Repository @Inject constructor(
    private val appsterdamService: AppsterdamService,
    private val database: AppsterdamDatabase
) {

    val error: MutableStateFlow<String> = MutableStateFlow(value = "")

    open fun getTeams(): Flow<List<Team>> = database.teamDao().getTeams()

    open fun getCategories(): Flow<List<Category>> = database.categoryDao().getCategories()

    open fun getJobs(): Flow<List<Job>> = database.jobDao().getJobs()

    open suspend fun refreshTeams() {
        try {
            val response: Response<App> = appsterdamService.getApp()
            if (response.code() == 200) {
                database.teamDao().insert(list = response.body()?.people ?: listOf())
            } else {
                error.value = response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE
            }
        } catch (e: Exception) {
            error.value = e.message ?: GENERAL_ERROR_MESSAGE
        }
    }

    open suspend fun refreshCategories() {
        val response: Response<List<Category>> = appsterdamService.getEvents()
        if (response.code() == 200) {
            database.categoryDao().insert(list = response.body() ?: listOf())
        } else {
            error.value = response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE
        }
    }

    open suspend fun refreshJobs() {
        val response: Response<List<Job>> = appsterdamService.getJobs()
        if (response.code() == 200 && response.body() != null) {
            database.jobDao().insert(list = response.body() ?: listOf())
        } else {
            error.value = response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE
        }
    }

    companion object {

        const val GENERAL_ERROR_MESSAGE: String = "General Error Message"
    }
}
