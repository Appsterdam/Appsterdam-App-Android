package rs.appsterdam.appsterdam.models

import com.google.gson.annotations.SerializedName

data class Appsterdamer (
    @SerializedName("home")
    var home: String? = null,
    @SerializedName("people")
    var people: ArrayList<People> = arrayListOf()
)