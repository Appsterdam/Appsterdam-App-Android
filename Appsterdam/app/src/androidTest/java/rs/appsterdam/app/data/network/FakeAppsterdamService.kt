package rs.appsterdam.app.data.network

import retrofit2.Response
import rs.appsterdam.app.data.model.App
import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.domain.repository.CATEGORIES
import rs.appsterdam.app.domain.repository.JOBS
import rs.appsterdam.app.domain.repository.TEAMS

class FakeAppsterdamService : AppsterdamService {

    override suspend fun getApp(): Response<App> = Response.success(App(people = TEAMS))

    override suspend fun getEvents(): Response<List<Category>> = Response.success(CATEGORIES)

    override suspend fun getJobs(): Response<List<Job>> = Response.success(JOBS)
}
