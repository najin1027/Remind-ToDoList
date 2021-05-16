package com.njs.remind_todolist.model;

import android.content.SharedPreferences;

public class SettingValue {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor spEditor;


    public String getBackgroundValue() {
        return sharedPreferences.getString("setting_background", null);
    }


    public int getTextSizeValue() {
        return sharedPreferences.getInt("setting_text_size", 0);
    }

    public int getTextAlignmentValue() {
        return sharedPreferences.getInt("setting_text alignment", 0);
    }


    public void setBackgroundValue(String value) {
        spEditor.putString("setting_background", value);
        spEditor.apply();
    }

    public void setTextSizeValue(int value) {
        spEditor.putInt("setting_text_size", value);
        spEditor.apply();
    }

    public void setTextAlignmentValue(int value) {
        spEditor.putInt("setting_text_alignment", value);
        spEditor.apply();
    }


}
