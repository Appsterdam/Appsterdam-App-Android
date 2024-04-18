package rs.appsterdam.app.presentation.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.appsterdam.app.databinding.ViewAboutFooterBinding

class FooterViewHolder private constructor(private val binding: ViewAboutFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun from(parent: ViewGroup): FooterViewHolder = FooterViewHolder(
            binding = ViewAboutFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
