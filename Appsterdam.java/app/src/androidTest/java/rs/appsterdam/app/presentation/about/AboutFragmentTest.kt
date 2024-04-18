package rs.appsterdam.app.presentation.about

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rs.appsterdam.app.R
import rs.appsterdam.app.domain.repository.MEMBERS
import rs.appsterdam.app.launchFragmentInHiltContainer

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class AboutFragmentTest {

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<AboutFragment> {
            navController.setGraph(R.navigation.mobile_navigation)
            navController.setCurrentDestination(R.id.navigation_about)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun about_fragment_is_displayed_with_expected_values() {
        Espresso.onView(ViewMatchers.withText(R.string.quote))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.mike_lee))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.appsterdam_team))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_discord))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_facebook))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_twitter))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_website))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_code_of_conduct))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_privacy_policy))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.all_rights_reserved))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.link_youtube))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(MEMBERS[0].name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun member_details_fragment_is_displayed_with_item_is_selected() {
        Espresso.onView(ViewMatchers.withId(R.id.members_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MemberItemViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        ViewMatchers.assertThat(
            navController.currentDestination?.id,
            Matchers.equalTo(R.id.navigation_member_details)
        )
    }
}
