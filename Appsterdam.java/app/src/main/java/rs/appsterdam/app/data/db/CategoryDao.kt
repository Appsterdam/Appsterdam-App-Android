package rs.appsterdam.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rs.appsterdam.app.data.model.Category

@Dao
interface CategoryDao {

    @Query("select * from category_table")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<Category>)
}
