package rs.appsterdam.app.data.network

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AppsterdamServiceTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var service: AppsterdamService

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun testGetApp(): TestResult = runTest {
        val response = service.getApp()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.errorBody() == null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.body() != null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.code() == 200
        )
    }

    @Test
    fun testGetJobs(): TestResult = runTest {
        val response = service.getJobs()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.errorBody() == null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.body() != null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.code() == 200
        )
    }

    @Test
    fun testGetEvent(): TestResult = runTest {
        val response = service.getEvents()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.errorBody() == null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.body() != null
        )
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            response.code() == 200
        )
    }
}
