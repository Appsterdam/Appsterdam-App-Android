package rs.appsterdam.app.presentation.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Member
import rs.appsterdam.app.data.model.Team
import rs.appsterdam.app.databinding.ViewTeamItemBinding

class TeamItemViewHolder private constructor(private val binding: ViewTeamItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Team, onItemClick: ((Member) -> Unit)) {
        binding.item = item
        val layoutManager = GridLayoutManager(binding.membersRecyclerView.context, 3)
        layoutManager.initialPrefetchItemCount = item.members.size
        val adapter = MembersAdapter(onItemClick = onItemClick)
        binding.membersRecyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.membersRecyclerView.layoutManager = layoutManager
        binding.adapter = adapter
        adapter.submitList(item.members)
        binding.executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): TeamItemViewHolder = TeamItemViewHolder(
            binding = ViewTeamItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
