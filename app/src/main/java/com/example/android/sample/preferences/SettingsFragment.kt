package com.example.android.sample.preferences

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.*

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        setSummary(getString(R.string.key_list_preference))
        setSummary(getString(R.string.key_multi_select_list_preference))
        setSummary(getString(R.string.key_drop_down_preference))
        setSummary(getString(R.string.key_edit_text_preference))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.let {
            setSummary(key)
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    // https://stackoverflow.com/questions/7017082/change-the-summary-of-a-listpreference-with-the-new-value-android
    private fun setSummary(key: String) {
        when (key) {
            getString(R.string.key_drop_down_preference) -> {
                val preference = findPreference<DropDownPreference>(key)
                val value = preference?.value
                value?.let {
                    preference.summary = it
                }
            }
            getString(R.string.key_list_preference) -> {
                val preference = findPreference<ListPreference>(key)
                val value = preference?.value
                value?.let {
                    preference.summary = it
                }
            }
            getString(R.string.key_multi_select_list_preference) -> {
                val preference = findPreference<MultiSelectListPreference>(key)
                val values = preference?.values
                values?.let {
                    preference.summary = it.toString()
                }
            }
            getString(R.string.key_edit_text_preference) -> {
                val preference = findPreference<EditTextPreference>(key)
                val value = preference?.text
                value?.let {
                    preference.summary = it.toString()
                }
            }
            else -> {
                // noop
            }
        }
    }
}