package rs.appsterdam.appsterdam.models

import com.google.gson.annotations.SerializedName


data class Members (
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("picture")
    var picture: String? = null,
    @SerializedName("function")
    var function: String? = null,
    @SerializedName("twitter")
    var twitter: String? = null,
    @SerializedName("linkedin")
    var linkedin: String? = null,
    @SerializedName("website")
    var website : String? = null,
    @SerializedName("bio")
    var bio: String? = null

)
