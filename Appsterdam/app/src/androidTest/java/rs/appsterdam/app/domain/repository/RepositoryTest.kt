package rs.appsterdam.app.domain.repository

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RepositoryTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: Repository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getCategories(): TestResult = runTest {
        repository.refreshCategories()
        val list = repository.getCategories().first()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            list == CATEGORIES
        )
    }

    @Test
    fun getJobs(): TestResult = runTest {
        repository.refreshJobs()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            repository.getJobs().first() == JOBS
        )
    }

    @Test
    fun getTeams(): TestResult = runTest {
        repository.refreshTeams()
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            repository.getTeams().first() == TEAMS
        )
    }
}
