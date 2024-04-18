package rs.appsterdam.app.presentation.home

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
import rs.appsterdam.app.launchFragmentInHiltContainer

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<HomeFragment>()
    }

    @Test
    fun home_fragment_is_displayed_with_expected_values() {
        Espresso.onView(ViewMatchers.withText(R.string.link_join_community))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.text_join_community))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_collaborate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.text_collaborate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_share_project))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.text_share_project))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
