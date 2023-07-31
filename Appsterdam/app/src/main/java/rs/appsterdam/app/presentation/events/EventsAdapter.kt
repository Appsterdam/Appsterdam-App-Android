package rs.appsterdam.app.presentation.events

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Event

class EventsAdapter(
    private var list: MutableList<StickyModel>,
    private val showDescriptions: Boolean,
    private val onItemClick: ((Event) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var filteredEventList: List<StickyModel> = list
    private var valueFilter: ValueFilter? = null

    override fun getItemCount(): Int = filteredEventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.Header.ordinal -> HeaderViewHolder.from(parent = parent)
            ViewType.Content.ordinal -> EventViewHolder.from(parent = parent)
            else -> throw IllegalArgumentException("Not a layout")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(item = filteredEventList[position] as HeaderModel)
            is EventViewHolder -> holder.bind(
                item = (filteredEventList[position] as ContentModel).event,
                showDescriptions = showDescriptions,
                onItemClick = onItemClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (filteredEventList[position]) {
        is HeaderModel -> ViewType.Header.ordinal
        is ContentModel -> ViewType.Content.ordinal
        else -> -1
    }

    override fun getFilter(): Filter {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter ?: ValueFilter()
    }

    inner class ValueFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val constraintString = constraint?.toString() ?: ""
            filteredEventList = if (constraintString.isEmpty()) {
                list
            } else {
                val filteredList: MutableList<StickyModel> = ArrayList()
                list.filter {
                    it is HeaderModel || it is ContentModel &&
                        it.event.description?.contains(constraintString) == true
                }.forEach {
                    filteredList.add(element = it)
                }
                filteredList
            }
            return FilterResults().apply { values = filteredEventList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredEventList = if (results?.values == null) {
                ArrayList()
            } else {
                results.values as ArrayList<StickyModel>
            }
            notifyDataSetChanged()
        }
    }
}
