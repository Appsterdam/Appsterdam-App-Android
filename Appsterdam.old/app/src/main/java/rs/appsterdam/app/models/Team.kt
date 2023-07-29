package rs.appsterdam.app.models

import com.google.gson.annotations.SerializedName


data class Team (

    @SerializedName("team")
    var teamName: String? = null,
    @SerializedName("members")
    var members: ArrayList<Member> = arrayListOf()

)
