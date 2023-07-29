package rs.appsterdam.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rs.appsterdam.app.data.model.Job

@Dao
interface JobDao {

    @Query("select * from job_table ORDER BY jobPublishEndDate DESC")
    fun getJobs(): Flow<List<Job>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<Job>)
}
