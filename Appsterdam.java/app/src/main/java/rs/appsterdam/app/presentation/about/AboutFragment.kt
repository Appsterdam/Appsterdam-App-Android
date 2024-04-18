package rs.appsterdam.app.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.appsterdam.app.databinding.FragmentAboutBinding
import rs.appsterdam.app.presentation.showSnackbar

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private val binding: FragmentAboutBinding by lazy {
        FragmentAboutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: AboutViewModel by viewModels()
        val adapter = TeamsAdapter { member ->
            findNavController().navigate(
                directions = AboutFragmentDirections.actionNavigationAboutToMemberDetailsFragment(
                    member
                )
            )
        }
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
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
        binding.teamsRecyclerView.scrollTo(0,0)
    }
}
