package rs.appsterdam.app.presentation.about

import androidx.recyclerview.widget.DiffUtil
import rs.appsterdam.app.data.model.Member

class MemberDiffCallback : DiffUtil.ItemCallback<Member>() {

    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean =
        oldItem.name == newItem.name
}
