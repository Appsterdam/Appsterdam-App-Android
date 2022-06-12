package rs.appsterdam.appsterdam.models

import com.google.gson.annotations.SerializedName


data class People (

    @SerializedName("team")
    var team: String? = null,
    @SerializedName("members")
    var members: ArrayList<Members> = arrayListOf()

)
