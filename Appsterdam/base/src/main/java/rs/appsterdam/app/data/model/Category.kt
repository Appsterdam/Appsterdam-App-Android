package rs.appsterdam.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey
    @Json(name = "name")
    var name: String = "",
    @Json(name = "events") var events: List<Event> = arrayListOf()
)

@Parcelize
@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey
    @Json(name = "id")
    var id: String = "",
    @Json(name = "organizer") var organizer: String? = null,
    @Json(name = "date") var date: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "description") var description: String? = null,
    @Json(name = "price") var price: String? = null,
    @Json(name = "location_name") var locationName: String? = null,
    @Json(name = "location_address") var locationAddress: String? = null,
    @Json(name = "icon") var icon: String? = null,
    @Json(name = "attendees") var attendees: String? = null,
    @Json(name = "latitude") var latitude: String? = null,
    @Json(name = "longitude") var longitude: String? = null
) : Parcelable
