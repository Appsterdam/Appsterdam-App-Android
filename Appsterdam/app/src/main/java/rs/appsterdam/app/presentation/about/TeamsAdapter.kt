package rs.appsterdam.app.presentation.about

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Member
import rs.appsterdam.app.data.model.Team

class TeamsAdapter(private val onItemClick: ((Member) -> Unit)) :
    ListAdapter<Team, RecyclerView.ViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW -> HeaderViewHolder.from(parent = parent)
            FOOTER_VIEW -> FooterViewHolder.from(parent = parent)
            else -> TeamItemViewHolder.from(parent = parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder
            is FooterViewHolder -> holder
            else -> (holder as TeamItemViewHolder).bind(
                item = currentList[position - 1],
                onItemClick = onItemClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_VIEW
            currentList.size + 1 -> FOOTER_VIEW
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        if (currentList.isEmpty()) {
            return 1
        }
        return currentList.size + 2
    }

    companion object {

        const val HEADER_VIEW: Int = 1
        const val FOOTER_VIEW: Int = 2
    }
}
