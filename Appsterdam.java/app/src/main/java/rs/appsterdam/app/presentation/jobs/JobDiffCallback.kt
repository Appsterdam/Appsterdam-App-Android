package rs.appsterdam.app.presentation.jobs

import androidx.recyclerview.widget.DiffUtil
import rs.appsterdam.app.data.model.Job

class JobDiffCallback : DiffUtil.ItemCallback<Job>() {

    override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean =
        oldItem.jobID == newItem.jobID
}
