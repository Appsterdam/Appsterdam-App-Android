package rs.appsterdam.app.presentation.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.databinding.ViewAboutHeaderBinding

class HeaderViewHolder private constructor(private val binding: ViewAboutHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun from(parent: ViewGroup): HeaderViewHolder = HeaderViewHolder(
            binding = ViewAboutHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
