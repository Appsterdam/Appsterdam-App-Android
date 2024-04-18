package rs.appsterdam.app.data.network

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import rs.appsterdam.app.BuildConfig
import javax.inject.Inject

@HiltAndroidTest
class RetrofitClientTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var retrofit: Retrofit

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun testRetrofitInstance() {
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            retrofit.baseUrl().url().toString() == BuildConfig.BASE_URL
        )
    }
}
