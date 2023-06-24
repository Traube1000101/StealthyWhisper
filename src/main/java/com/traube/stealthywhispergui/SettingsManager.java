package com.traube.stealthywhispergui;

import java.util.prefs.Preferences;

public class SettingsManager {
    private static final String preferenceId = "com.traube.stealthywhispergui";

    public static void saveSetting(String key, String value) {
        Preferences preferences = Preferences.userRoot().node(preferenceId);
        preferences.put(key, value);
    }

    public static String getSetting(String key, String defaultValue) {
        Preferences preferences = Preferences.userRoot().node(preferenceId);
        return preferences.get(key, defaultValue);
    }
}

