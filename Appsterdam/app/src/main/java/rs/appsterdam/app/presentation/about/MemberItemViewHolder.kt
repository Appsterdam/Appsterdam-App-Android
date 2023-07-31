package rs.appsterdam.app.presentation.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Member
import rs.appsterdam.app.databinding.ViewMemberItemBinding

class MemberItemViewHolder private constructor(private val binding: ViewMemberItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Member, onItemClick: ((Member) -> Unit)) {
        binding.item = item
        binding.root.setOnClickListener {
            onItemClick(item)
        }
        binding.executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): MemberItemViewHolder = MemberItemViewHolder(
            binding = ViewMemberItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
