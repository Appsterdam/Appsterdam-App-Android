package rs.appsterdam.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "app_table")
data class App(
    @PrimaryKey
    @Json(name = "home")
    var home: String = "",
    @Json(name = "people") var people: List<Team> = arrayListOf()
)

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey
    @Json(name = "team")
    var team: String = "",
    @Json(name = "members") var members: List<Member> = arrayListOf()
)

@Parcelize
@Entity(tableName = "member_table")
data class Member(
    @PrimaryKey
    @Json(name = "name")
    var name: String = "",
    @Json(name = "picture") var picture: String? = null,
    @Json(name = "function") var function: String? = null,
    @Json(name = "twitter") var twitter: String? = null,
    @Json(name = "linkedin") var linkedin: String? = null,
    @Json(name = "website") var website: String? = null,
    @Json(name = "bio") var bio: String? = null
) : Parcelable
