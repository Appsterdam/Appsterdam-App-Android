package rs.appsterdam.app.presentation.about

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import rs.appsterdam.app.domain.repository.Repository
import rs.appsterdam.app.domain.repository.TEAMS
import rs.appsterdam.app.presentation.MainCoroutineRule

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class AboutViewModelTest {

    private val repository = Mockito.mock<Repository>()

    @get:Rule
    val mainCoroutineRule: MainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private lateinit var viewModel: AboutViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = AboutViewModel(repository = repository)
    }

    @Test
    fun loading_state_for_about_works(): Unit = runTest {
        MatcherAssert.assertThat(viewModel.loading.value, Matchers.`is`(true))
    }

    @Test
    fun success_state_for_about_works(): Unit = runTest {
        Mockito.`when`(repository.getTeams())
            .thenReturn(MutableStateFlow(value = TEAMS))
        MatcherAssert.assertThat(viewModel.loading.value, Matchers.`is`(true))
        viewModel.fetchData()
        advanceUntilIdle()
        MatcherAssert.assertThat(viewModel.flow.value, Matchers.`is`((TEAMS)))
    }
}
