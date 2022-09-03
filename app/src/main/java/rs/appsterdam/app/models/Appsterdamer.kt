package rs.appsterdam.app.models

import com.google.gson.annotations.SerializedName

data class Appsterdamer (
    @SerializedName("home")
    var home: String? = null,
    @SerializedName("people")
    var teams: ArrayList<Team> = arrayListOf()
)