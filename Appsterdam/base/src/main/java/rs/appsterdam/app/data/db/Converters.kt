package rs.appsterdam.app.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import rs.appsterdam.app.data.model.Event
import rs.appsterdam.app.data.model.Member

class Converters {

    @TypeConverter
    fun memberListToJsonString(src: List<Member>?): String = Gson().toJson(src)

    @TypeConverter
    fun jsonStringToMemberList(json: String): List<Member> =
        Gson().fromJson(json, Array<Member>::class.java).toList()

    @TypeConverter
    fun eventListToJsonString(src: List<Event>?): String = Gson().toJson(src)

    @TypeConverter
    fun jsonStringToEventList(json: String): List<Event> =
        Gson().fromJson(json, Array<Event>::class.java).toList()
}
