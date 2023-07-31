package rs.appsterdam.app.presentation.jobs

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.appsterdam.app.data.model.Job

class JobsAdapter(private val onItemClick: ((Job?) -> Unit)) :
    ListAdapter<Job, JobItemViewHolder>(JobDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobItemViewHolder =
        JobItemViewHolder.from(parent = parent)

    override fun onBindViewHolder(holder: JobItemViewHolder, position: Int) {
        holder.bind(item = getItem(position), onItemClick = onItemClick)
    }
}
