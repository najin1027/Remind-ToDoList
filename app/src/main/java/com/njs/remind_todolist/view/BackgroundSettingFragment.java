package com.njs.remind_todolist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.njs.remind_todolist.R;
import com.njs.remind_todolist.databinding.FragmentSettingBackgroundBinding;
import com.njs.remind_todolist.model.SettingValue;

public class BackgroundSettingFragment extends Fragment {
    private FragmentSettingBackgroundBinding binding;
    private View rootView;
    private String selectBackgroundValue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_background, container, false);
        rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rootView.findViewById(R.id.select_background_color_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingValue.setBackgroundValue(selectBackgroundValue);
            }
        });

        RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        switch (SettingValue.getBackgroundValue()) {
            case "light_brown":
                radioGroup.check(R.id.color_light_brown);
                break;
            case "dark_brown":
                radioGroup.check(R.id.color_dark_brown);
                break;
            case "reddish_brown":
                radioGroup.check(R.id.color_reddish_brown);
                break;
            case "green":
                radioGroup.check(R.id.color_green);
                break;
            case "blue":
                radioGroup.check(R.id.color_blue);
                break;
            case "violet":
                radioGroup.check(R.id.color_violet);
                break;
            case "charcoal":
                radioGroup.check(R.id.color_charcoal);
                break;
            case "black":
                radioGroup.check(R.id.color_black);
                break;
            case "white":
                radioGroup.check(R.id.color_white);
                break;
            case "light_pink":
                radioGroup.check(R.id.color_light_pink);
                break;
            case "light_yellow":
                radioGroup.check(R.id.color_light_yellow);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.color_light_brown:
                        selectBackgroundValue = "light_brown";
                        break;
                    case R.id.color_dark_brown:
                        selectBackgroundValue = "dark_brown";
                        break;
                    case R.id.color_reddish_brown:
                        selectBackgroundValue = "reddish_brown";
                        break;
                    case R.id.color_green:
                        selectBackgroundValue = "green";
                        break;
                    case R.id.color_blue:
                        selectBackgroundValue = "blue";
                        break;
                    case R.id.color_violet:
                        selectBackgroundValue = "violet";
                        break;
                    case R.id.color_charcoal:
                        selectBackgroundValue = "charcoal";
                        break;
                    case R.id.color_black:
                        selectBackgroundValue = "black";
                        break;
                    case R.id.color_white:
                        selectBackgroundValue = "white";
                        break;
                    case R.id.color_light_pink:
                        selectBackgroundValue = "light_pink";
                        break;
                    case R.id.color_light_yellow:
                        selectBackgroundValue = "light_yellow";
                        break;

                }
            }
        });
    }

    public static BackgroundSettingFragment newInstance() {
        return new BackgroundSettingFragment();
    }
}
