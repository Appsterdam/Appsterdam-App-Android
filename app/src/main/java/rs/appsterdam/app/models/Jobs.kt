package rs.appsterdam.app.models
import com.google.gson.annotations.SerializedName

data class Jobs (
    @SerializedName("jobUrl")
    var jobUrl: String? = null,
        @SerializedName("JobTitle")
    var JobTitle: String? = null,
    @SerializedName("JobShortDescription")
    var JobShortDescription: String? = null,
    @SerializedName("JobDescription")
    var JobDescription: String? = null,
    @SerializedName("JobPublishStartDate")
    var JobPublishStartDate: String? = null,
    @SerializedName("JobPublishEndDate")
    var JobPublishEndDate : String? = null,
    @SerializedName("JobProvider")
    var JobProvider: String? = null,
    @SerializedName("JobCity")
    var JobCity: String? = null
)