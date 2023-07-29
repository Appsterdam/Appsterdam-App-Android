package rs.appsterdam.app.domain.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.appsterdam.app.BuildConfig
import rs.appsterdam.app.data.db.AppsterdamDatabase
import rs.appsterdam.app.data.network.AppsterdamService
import rs.appsterdam.app.domain.datastore.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppsterdamDatabase =
        Room.databaseBuilder(
            context = context,
            klass = AppsterdamDatabase::class.java,
            name = "resume_database"
        ).build()

    @Singleton
    @Provides
    fun provideAppsterdamService(): AppsterdamService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(AppsterdamService::class.java)
    }

    @Provides
    fun provideDatastoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context = context)
}
