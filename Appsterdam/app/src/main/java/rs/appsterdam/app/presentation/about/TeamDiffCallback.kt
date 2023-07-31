package rs.appsterdam.app.presentation.about

import androidx.recyclerview.widget.DiffUtil
import rs.appsterdam.app.data.model.Team

class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {

    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean =
        oldItem.team == newItem.team
}
