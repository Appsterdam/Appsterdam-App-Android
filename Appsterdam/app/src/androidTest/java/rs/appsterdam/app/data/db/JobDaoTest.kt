package rs.appsterdam.app.data.db

import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rs.appsterdam.app.domain.repository.JOBS
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class JobDaoTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppsterdamDatabase

    private lateinit var dao: JobDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.jobDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCategories(): TestResult = runTest {
        dao.insert(list = JOBS)
        MatcherAssert.assertThat(
            "Actual and expected values are not the same",
            dao.getJobs().first() == JOBS
        )
    }
}
