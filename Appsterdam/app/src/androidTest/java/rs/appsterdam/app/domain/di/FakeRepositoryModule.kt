package rs.appsterdam.app.domain.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.appsterdam.app.BuildConfig
import rs.appsterdam.app.data.db.AppsterdamDatabase
import rs.appsterdam.app.data.network.AppsterdamService
import rs.appsterdam.app.data.network.FakeAppsterdamService
import rs.appsterdam.app.domain.datastore.DataStoreManager
import rs.appsterdam.app.domain.repository.Repository
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {

    @Named("test_db")
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppsterdamDatabase =
        Room.inMemoryDatabaseBuilder(
            context = context,
            klass = AppsterdamDatabase::class.java
        ).allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideAppsterdamService(): AppsterdamService = FakeAppsterdamService()

    @Singleton
    @Provides
    fun provideRepository(
        appsterdamService: AppsterdamService,
        @Named("test_db")
        appsterdamDatabase: AppsterdamDatabase
    ): Repository = Repository(
        appsterdamService = appsterdamService,
        database = appsterdamDatabase
    )

    @Provides
    fun provideDatastoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context = context)
}
