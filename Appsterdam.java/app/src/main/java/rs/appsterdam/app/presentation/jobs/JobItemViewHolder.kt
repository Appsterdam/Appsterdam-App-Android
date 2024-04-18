package rs.appsterdam.app.presentation.jobs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.databinding.ViewJobItemBinding

class JobItemViewHolder private constructor(private val binding: ViewJobItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Job, onItemClick: ((Job?) -> Unit)) {
        binding.item = item
        binding.root.setOnClickListener {
            onItemClick(item)
        }
        binding.executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): JobItemViewHolder = JobItemViewHolder(
            binding = ViewJobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
