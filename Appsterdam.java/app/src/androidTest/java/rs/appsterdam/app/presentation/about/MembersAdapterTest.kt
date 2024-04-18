package rs.appsterdam.app.presentation.about

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import rs.appsterdam.app.domain.repository.MEMBERS

class MembersAdapterTest {

    @Test
    fun members_adapter_is_receiving_the_correct_number_of_items() {
        val adapter = MembersAdapter {}
        adapter.submitList(MEMBERS)
        MatcherAssert.assertThat(adapter.itemCount, Matchers.`is`((MEMBERS.size)))
    }
}
