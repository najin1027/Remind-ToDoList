package com.njs.remind_todolist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.njs.remind_todolist.R;
import com.njs.remind_todolist.databinding.FragmentSettingBackgroundBinding;

public class BackgroundSettingFragment extends Fragment {
    FragmentSettingBackgroundBinding binding;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_background , container , false);
        rootView = binding.getRoot();
        return rootView;
    }


    public static BackgroundSettingFragment newInstance() {
        return new BackgroundSettingFragment();
    }
}
