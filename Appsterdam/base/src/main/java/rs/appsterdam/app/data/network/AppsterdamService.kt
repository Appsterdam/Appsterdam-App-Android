package rs.appsterdam.app.data.network

import retrofit2.Response
import retrofit2.http.GET
import rs.appsterdam.app.data.model.App
import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.data.model.Job

interface AppsterdamService {

    @GET("app.json")
    suspend fun getApp(): Response<App>

    @GET("events.json")
    suspend fun getEvents(): Response<List<Category>>

    @GET("jobs.json")
    suspend fun getJobs(): Response<List<Job>>
}
