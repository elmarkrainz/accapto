package org.accapto.accessibilitypatternlib.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.accapto.accessibilitypatternlib.R;

/**
 * Created by krajn on 29/10/17.
 */

public class AccessibilityPreferences {

    private final String SPEECH_OUTPUT = "speechoutput";
    private static final String SHARED_SETTINGS = "Profile_Settings";


    private SharedPreferences accessibilityPrefernces;
    private boolean enableSpeechOutput;


    public AccessibilityPreferences(Context c) {
        accessibilityPrefernces = c.getSharedPreferences(SHARED_SETTINGS, 0);
        loadSettings();
    }


    private void loadSettings() {

        this.enableSpeechOutput = (accessibilityPrefernces.getBoolean(SPEECH_OUTPUT, false));


    }

    private void saveSettings() {

        // save to shared Prefs
        SharedPreferences.Editor edPrfs = accessibilityPrefernces.edit(); // Prefs Editor

        // values
        edPrfs.putBoolean(SPEECH_OUTPUT, this.enableSpeechOutput);
        edPrfs.commit();
    }




    public boolean isEnableSpeechOutput() {
        return enableSpeechOutput;
    }

    public void setEnableSpeechOutput(boolean enableSpeechOutput) {
        this.enableSpeechOutput = enableSpeechOutput;
        saveSettings();
    }


}
