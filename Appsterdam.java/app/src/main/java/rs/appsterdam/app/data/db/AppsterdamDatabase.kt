package rs.appsterdam.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.data.model.Team

@Database(
    entities = [Team::class, Category::class, Job::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppsterdamDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    abstract fun categoryDao(): CategoryDao

    abstract fun jobDao(): JobDao
}
