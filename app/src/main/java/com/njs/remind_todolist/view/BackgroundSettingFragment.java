package com.njs.remind_todolist.view;

import android.os.Bundle;
import android.view.Gravity;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
                Toast toast = Toast.makeText(getContext(), R.string.re_start_message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                getActivity().onBackPressed();
            }
        });
        HashMap<String, Integer> settingValuesMap = new HashMap<String, Integer>() {{
            put("light_brown", R.id.color_light_brown);
            put("dark_brown", R.id.color_dark_brown);
            put("reddish_brown", R.id.color_reddish_brown);
            put("green", R.id.color_green);
            put("blue", R.id.color_blue);
            put("violet", R.id.color_violet);
            put("charcoal", R.id.color_charcoal);
            put("black", R.id.color_black);
            put("white", R.id.color_white);
            put("light_pink", R.id.color_light_pink);
            put("light_yellow", R.id.color_light_yellow);
        }};
        Iterator<Map.Entry<String, Integer>> entryIterator = settingValuesMap.entrySet().iterator();
        RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        while (entryIterator.hasNext()) {
            Map.Entry<String, Integer> entry = entryIterator.next();
            if (SettingValue.getBackgroundValue().equalsIgnoreCase(entry.getKey())) {
                radioGroup.check(entry.getValue());
            }
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
