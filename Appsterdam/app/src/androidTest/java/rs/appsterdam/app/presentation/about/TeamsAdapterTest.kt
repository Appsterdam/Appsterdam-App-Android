package rs.appsterdam.app.presentation.about

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import rs.appsterdam.app.domain.repository.TEAMS

class TeamsAdapterTest {

    @Test
    fun teams_adapter_is_receiving_the_correct_number_of_items() {
        val adapter = TeamsAdapter {}
        adapter.submitList(TEAMS)
        MatcherAssert.assertThat(adapter.itemCount, Matchers.`is`((TEAMS.size + 2)))
    }
}
