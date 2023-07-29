package rs.appsterdam.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "job_table")
data class Job(
    @Json(name = "JobUrl") var jobUrl: String? = null,
    @Json(name = "JobTitle") var jobTitle: String? = null,
    @Json(name = "JobDescription") var jobDescription: String? = null,
    @Json(name = "JobShortDescription") var jobShortDescription: String? = null,
    @Json(name = "JobPublishEndDate") var jobPublishEndDate: String? = null,
    @PrimaryKey
    @Json(name = "JobID")
    var jobID: String = "",
    @Json(name = "JobProvider") var jobProvider: String? = null,
    @Json(name = "JobCity") var jobCity: String? = null
)
