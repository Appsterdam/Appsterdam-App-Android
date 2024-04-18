package rs.appsterdam.app.presentation.jobs

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import rs.appsterdam.app.domain.repository.JOBS

class JobsAdapterTest {

    @Test
    fun jobs_adapter_is_receiving_the_correct_number_of_items() {
        val adapter = JobsAdapter {}
        adapter.submitList(JOBS)
        MatcherAssert.assertThat(adapter.itemCount, Matchers.`is`((JOBS.size)))
    }
}
