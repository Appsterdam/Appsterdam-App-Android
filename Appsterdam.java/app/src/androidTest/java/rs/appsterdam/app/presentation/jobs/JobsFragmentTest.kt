package rs.appsterdam.app.presentation.jobs

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rs.appsterdam.app.domain.repository.JOBS
import rs.appsterdam.app.launchFragmentInHiltContainer

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class JobsFragmentTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<JobsFragment>()
    }

    @Test
    fun jobs_fragment_is_displayed_with_expected_values() {
        Espresso.onView(ViewMatchers.withText(JOBS[0].jobTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
