package rs.appsterdam.app.presentation.events

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import rs.appsterdam.app.domain.repository.CATEGORIES

class EventsAdapterTest {

    @Test
    fun events_adapter_is_receiving_the_correct_number_of_items() {
        val list = mutableListOf<StickyModel>()
        for ((name, events) in CATEGORIES) {
            list.add(element = HeaderModel(name))
            for (event in events) {
                list.add(element = ContentModel(name, event))
            }
        }
        val adapter = EventsAdapter(list = list, showDescriptions = true) {}
        MatcherAssert.assertThat(adapter.itemCount, Matchers.`is`((list.size)))
    }
}
