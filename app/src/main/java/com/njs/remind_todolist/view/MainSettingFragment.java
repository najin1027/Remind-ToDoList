package com.njs.remind_todolist.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.njs.remind_todolist.R;


public class MainSettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    private Preference settingBackground;
    private Preference settingTextSize;
    private Preference settingAlignment;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        setPreference();
    }

    public static MainSettingFragment newInstance() {
        return new MainSettingFragment();
    }

    @SuppressLint("CommitPrefEdits")
    private void setPreference() {
        settingBackground = findPreference("setting_background");
        settingTextSize = findPreference("setting_text_size");
        settingAlignment = findPreference("setting_text alignment");
        settingBackground.setOnPreferenceClickListener(this);
        settingTextSize.setOnPreferenceClickListener(this);
        settingAlignment.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "setting_background" :
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame , BackgroundSettingFragment.newInstance())
                        .commit();
                break;
            case "setting_text_size" :
                break;
            case "setting_text alignment" :
                break;
        }
        return false;
    }
}
