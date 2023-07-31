package rs.appsterdam.app.presentation.about

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.appsterdam.app.data.model.Member

class MembersAdapter(private val onItemClick: ((Member) -> Unit)) :
    ListAdapter<Member, MemberItemViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberItemViewHolder {
        return MemberItemViewHolder.from(parent = parent)
    }

    override fun onBindViewHolder(holder: MemberItemViewHolder, position: Int) {
        holder.bind(item = getItem(position), onItemClick = onItemClick)
    }
}
