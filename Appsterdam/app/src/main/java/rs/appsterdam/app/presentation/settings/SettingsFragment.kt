package rs.appsterdam.app.presentation.settings

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.appsterdam.app.R
import rs.appsterdam.app.domain.datastore.DataStoreManager
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setOnPreferenceChangeListener(findPreference(getString(R.string.title_show_descriptions)) as CheckBoxPreference?)
        setOnPreferenceChangeListener(findPreference(getString(R.string.title_enable_search)) as CheckBoxPreference?)
        setOnPreferenceChangeListener(findPreference(getString(R.string.title_notify_on_new_events)) as CheckBoxPreference?)
        setOnPreferenceChangeListener(findPreference(getString(R.string.title_enable_jobs)) as CheckBoxPreference?)
        setOnPreferenceChangeListener(findPreference(getString(R.string.title_notify_on_new_jobs)) as CheckBoxPreference?)
    }

    private fun setOnPreferenceChangeListener(preference: CheckBoxPreference?) {
        preference?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                when (preference.key) {
                    getString(R.string.title_show_descriptions) -> {
                        dataStoreManager.saveShowDescriptions(value = newValue as Boolean)
                    }

                    getString(R.string.title_enable_search) -> {
                        dataStoreManager.saveEnableSearch(value = newValue as Boolean)
                    }

                    getString(R.string.title_enable_jobs) -> {
                        dataStoreManager.saveEnableJobs(value = newValue as Boolean)
                    }

                    getString(R.string.title_notify_on_new_events) -> {
                        dataStoreManager.saveNotifyOnNewEvents(value = newValue as Boolean)
                    }

                    else -> {
                        dataStoreManager.saveNotifyOnNewJobs(value = newValue as Boolean)
                    }
                }
            }
        }
        requireActivity().recreate()
        return true
    }
}
