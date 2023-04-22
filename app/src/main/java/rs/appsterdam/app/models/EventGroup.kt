package rs.appsterdam.app.models
import com.google.gson.annotations.SerializedName


data class EventGroup (
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("events")
    var events: List<Event>? = null
)

data class Event (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("organizer")
    var organizer: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("location_name")
    var location_name: String? = null,
    @SerializedName("location_address")
    var location_address: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("attendees")
    var attendees: String? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null
)