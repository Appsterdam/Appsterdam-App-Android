package rs.appsterdam.app.presentation.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Event
import rs.appsterdam.app.databinding.ViewEventItemBinding

class EventViewHolder private constructor(private val binding: ViewEventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Event,
        showDescriptions: Boolean,
        onItemClick: ((Event) -> Unit)
    ) {
        binding.item = item
        binding.root.setOnClickListener {
            onItemClick(item)
        }
        binding.showDescriptions = showDescriptions
        binding.executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): EventViewHolder = EventViewHolder(
            binding = ViewEventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
