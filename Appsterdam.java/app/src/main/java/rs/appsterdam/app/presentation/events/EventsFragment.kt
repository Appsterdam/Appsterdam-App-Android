package rs.appsterdam.app.presentation.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.appsterdam.app.R
import rs.appsterdam.app.databinding.FragmentEventsBinding
import rs.appsterdam.app.presentation.showSnackbar

@AndroidEntryPoint
class EventsFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

    private val binding: FragmentEventsBinding by lazy {
        FragmentEventsBinding.inflate(layoutInflater)
    }

    lateinit var adapter: EventsAdapter

    private var showDescriptions: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsViewModel by viewModels<EventsSettingsViewModel>()
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.isSearchEnabled().collect {
                if (it) {
                    requireActivity().addMenuProvider(
                        this@EventsFragment,
                        viewLifecycleOwner,
                        Lifecycle.State.RESUMED
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.showDescriptions().collect {
                showDescriptions = it
            }
        }
        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel by viewModels<EventsViewModel>()
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
            val list: MutableList<StickyModel> = mutableListOf()
            viewModel.flow.collect {
                for ((name, events) in it) {
                    list.add(element = HeaderModel(header = name))
                    for (event in events) {
                        list.add(element = ContentModel(header = name, event = event))
                    }
                }
                val adapter =
                    EventsAdapter(list = list, showDescriptions = showDescriptions) { event ->
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.meetup_url, event.id))
                            )
                        )
                    }
                binding.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.fetchData()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_events, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> {
                val searchView: SearchView = menuItem.actionView as SearchView
                searchView.setOnQueryTextListener(this)
                true
            }

            else -> false
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        adapter.filter.filter(newText)
        return false
    }
}
