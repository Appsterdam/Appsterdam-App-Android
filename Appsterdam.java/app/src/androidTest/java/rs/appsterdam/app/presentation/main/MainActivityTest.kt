package rs.appsterdam.app.presentation.main

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import rs.appsterdam.app.R

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = activityScenarioRule()

    @Test
    fun click_on_about_navigation_item_goes_to_fragment() {
        click_on_navigation_item_goes_to_fragment(
            idRes = R.id.navigation_about,
            stringRes = R.string.title_about
        )
    }

    @Test
    fun click_on_events_navigation_item_goes_to_fragment() {
        click_on_navigation_item_goes_to_fragment(
            idRes = R.id.navigation_events,
            stringRes = R.string.title_events
        )
    }

    @Test
    fun click_on_home_navigation_item_goes_to_fragment() {
        click_on_navigation_item_goes_to_fragment(
            idRes = R.id.navigation_home,
            stringRes = R.string.title_home
        )
    }

    @Test
    fun click_on_jobs_navigation_item_goes_to_fragment() {
        click_on_navigation_item_goes_to_fragment(
            idRes = R.id.navigation_jobs,
            stringRes = R.string.title_jobs
        )
    }

    private fun click_on_navigation_item_goes_to_fragment(idRes: Int, stringRes: Int) {
        launchActivity<MainActivity>().use {
            Espresso.onView(ViewMatchers.withId(idRes))
                .perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.toolbar))
                .check(
                    ViewAssertions.matches(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(stringRes))
                    )
                )
        }
    }
}
