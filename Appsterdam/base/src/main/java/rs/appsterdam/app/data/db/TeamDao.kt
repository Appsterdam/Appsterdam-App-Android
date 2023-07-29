package rs.appsterdam.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rs.appsterdam.app.data.model.Team

@Dao
interface TeamDao {

    @Query("select * from team_table")
    fun getTeams(): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<Team>)
}
