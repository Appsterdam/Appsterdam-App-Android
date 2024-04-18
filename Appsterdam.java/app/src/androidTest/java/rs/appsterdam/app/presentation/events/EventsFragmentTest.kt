package rs.appsterdam.app.presentation.events

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rs.appsterdam.app.R
import rs.appsterdam.app.domain.repository.EVENTS
import rs.appsterdam.app.launchFragmentInHiltContainer

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class EventsFragmentTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<EventsFragment>()
    }

    @Test
    fun events_fragment_is_displayed_with_expected_values() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(EVENTS.first().name))
                )
            )
    }
}
