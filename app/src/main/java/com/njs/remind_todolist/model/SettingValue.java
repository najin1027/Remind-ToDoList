package com.njs.remind_todolist.model;

import android.content.SharedPreferences;

public class SettingValue {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor spEditor;


    public static String getBackgroundValue() {
        return sharedPreferences.getString("setting_background", null);
    }


    public static int getTextSizeValue() {
        return sharedPreferences.getInt("setting_text_size", 0);
    }

    public static int getTextAlignmentValue() {
        return sharedPreferences.getInt("setting_text_alignment", 0);
    }


    public static void setBackgroundValue(String value) {
        spEditor.putString("setting_background", value);
        spEditor.apply();
    }

    public static void setTextSizeValue(int value) {
        spEditor.putInt("setting_text_size", value);
        spEditor.apply();
    }

    public static void setTextAlignmentValue(int value) {
        spEditor.putInt("setting_text_alignment", value);
        spEditor.apply();
    }


}
