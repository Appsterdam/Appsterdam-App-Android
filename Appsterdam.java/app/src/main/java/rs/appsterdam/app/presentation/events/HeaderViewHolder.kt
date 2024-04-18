package rs.appsterdam.app.presentation.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.databinding.ViewHeaderItemBinding

class HeaderViewHolder private constructor(private val binding: ViewHeaderItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HeaderModel) {
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): HeaderViewHolder = HeaderViewHolder(
            binding = ViewHeaderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
