package rs.appsterdam.app.presentation.jobs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.appsterdam.app.databinding.FragmentJobsBinding
import rs.appsterdam.app.presentation.showSnackbar

@AndroidEntryPoint
class JobsFragment : Fragment() {

    private val binding: FragmentJobsBinding by lazy {
        FragmentJobsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<JobsViewModel>()
        val adapter = JobsAdapter {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it?.jobUrl)))
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.adapter = adapter
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect {
                    if (it.isNotEmpty()) {
                        binding.root.showSnackbar(message = it)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collect {
                adapter.submitList(it)
            }
        }
        viewModel.fetchData()
    }
}
