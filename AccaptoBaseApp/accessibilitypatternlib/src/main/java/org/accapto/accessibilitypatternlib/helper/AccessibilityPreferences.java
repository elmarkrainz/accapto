package org.accapto.accessibilitypatternlib.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.accapto.accessibilitypatternlib.R;

/**
 * Warpper for accessibility settings
 *
 * Created by krajn on 29/10/17.
 */

public class AccessibilityPreferences {

    private static final String SPEECH_INPUT = "speech_input";
    private final String SPEECH_OUTPUT = "speech_output";
    private static final String SHARED_SETTINGS = "Profile_Settings";


    private SharedPreferences accessibilityPrefernces;
    private boolean enableSpeechOutput;
    private boolean enableSpeechInput;


    public AccessibilityPreferences(Context c) {
        accessibilityPrefernces = c.getSharedPreferences(SHARED_SETTINGS, 0);
        loadSettings();
    }


    private void loadSettings() {
        this.enableSpeechOutput = (accessibilityPrefernces.getBoolean(SPEECH_OUTPUT, false));
        this.enableSpeechInput = (accessibilityPrefernces.getBoolean(SPEECH_INPUT, false));
    }


    private void saveSettings() {
        // save to shared Prefs
        SharedPreferences.Editor edPrfs = accessibilityPrefernces.edit(); // Prefs Editor

        // values
        edPrfs.putBoolean(SPEECH_OUTPUT, this.enableSpeechOutput);
        edPrfs.putBoolean(SPEECH_INPUT, this.enableSpeechInput);
        edPrfs.commit();
    }


    public boolean isEnableSpeechOutput() {
        return enableSpeechOutput;
    }


    public void setEnableSpeechOutput(boolean enableSpeechOutput) {
        this.enableSpeechOutput = enableSpeechOutput;
        saveSettings();
    }


    public boolean isEnableSpeechInput() {
        return enableSpeechInput;
    }


    public void setEnableSpeechInput(boolean enableSpeechInput) {
        this.enableSpeechInput = enableSpeechInput;
        saveSettings();
    }
}
