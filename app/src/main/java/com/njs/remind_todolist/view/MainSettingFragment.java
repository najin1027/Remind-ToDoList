package com.njs.remind_todolist.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.njs.remind_todolist.R;
import com.njs.remind_todolist.model.SettingValue;


public class MainSettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    private Preference settingBackground;
    private Preference settingTextSize;
    private Preference settingTextAlignment;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        setPreference();
        setSummary();
    }

    public static MainSettingFragment newInstance() {
        return new MainSettingFragment();
    }

    @SuppressLint("CommitPrefEdits")
    private void setPreference() {
        settingBackground = findPreference("setting_background");
        settingTextSize = findPreference("setting_text_size");
        settingTextAlignment = findPreference("setting_text_alignment");
        settingBackground.setOnPreferenceClickListener(this);
        settingTextSize.setOnPreferenceClickListener(this);
        settingTextAlignment.setOnPreferenceClickListener(this);
    }

    private void setSummary() {
        switch (SettingValue.getTextSizeValue()) {
            case 1:
                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[0]);
                break;
            case 2:
                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[1]);
                break;
            case 3:
                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[2]);
                break;
        }

        switch (SettingValue.getTextAlignmentValue()) {
            case 1:
                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[0]);
                break;
            case 2:
                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[1]);
                break;
            case 3:
                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[2]);
                break;
        }
    }

    private void showTextSizeSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_text_setting, null);
        final Spinner spinner = view.findViewById(R.id.text_setting_select_spinner);
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_item_text_size,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        builder.setView(view)
                .setPositiveButton(R.string.dialog_check, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (spinner.getSelectedItemPosition()) {
                            case 0:
                                SettingValue.setTextSizeValue(1);
                                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[0]);
                                break;
                            case 1:
                                SettingValue.setTextSizeValue(2);
                                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[1]);
                                break;
                            case 2:
                                SettingValue.setTextSizeValue(3);
                                settingTextSize.setSummary(getResources().getStringArray(R.array.spinner_item_text_size)[2]);
                                break;
                        }
                        showReStartToastMessage();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null);

        switch (SettingValue.getTextSizeValue()) {
            case 1:
                spinner.setSelection(0);
                break;
            case 2:
                spinner.setSelection(1);
                break;
            case 3:
                spinner.setSelection(2);
                break;
        }
        builder.show();
    }


    private void showTextAlignmentSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_text_setting, null);
        final Spinner spinner = view.findViewById(R.id.text_setting_select_spinner);
        final TextView textView = view.findViewById(R.id.title_dialog);
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_item_text_alignment,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        textView.setText(R.string.text_alignment_setting_dialog_title);
        builder.setView(view)
                .setPositiveButton(R.string.dialog_check, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (spinner.getSelectedItemPosition()) {
                            case 0:
                                SettingValue.setTextAlignmentValue(1);
                                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[0]);
                                break;
                            case 1:
                                SettingValue.setTextAlignmentValue(2);
                                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[1]);
                                break;
                            case 2:
                                SettingValue.setTextAlignmentValue(3);
                                settingTextAlignment.setSummary(getResources().getStringArray(R.array.spinner_item_text_alignment)[2]);
                                break;
                        }
                            showReStartToastMessage();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null);

        switch (SettingValue.getTextAlignmentValue()) {
            case 1:
                spinner.setSelection(0);
                break;
            case 2:
                spinner.setSelection(1);
                break;
            case 3:
                spinner.setSelection(2);
                break;
        }
        builder.show();
    }

    private void showReStartToastMessage() {
        Toast toast = Toast.makeText(getContext(), R.string.re_start_message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "setting_background":
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame, BackgroundSettingFragment.newInstance())
                        .commit();
                break;
            case "setting_text_size":
                showTextSizeSettingDialog();
                break;
            case "setting_text_alignment":
                showTextAlignmentSettingDialog();
                break;
        }
        return false;
    }
}
