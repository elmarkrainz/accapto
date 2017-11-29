package org.accapto.accessibilitypatternlib;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.accapto.accessibilitypatternlib.helper.AccessibilityPreferences;
import org.accapto.accessibilitypatternlib.helper.SpeechInputHelper;
import org.accapto.accessibilitypatternlib.helper.SpeechOutputHelper;
import org.accapto.accessibilitypatternlib.helper.ThemeChanger;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for accessibility support
 * <p>
 * has automatic included
 * - changing theme at runtime
 * - enable Speechoutput
 * - enable SpeechInput
 * <p>
 * <p>
 * Created by EKrainz
 */

public abstract class AccaptoBaseActivity extends AppCompatActivity {


    protected String screenName;
    protected String screenDescription;

    protected SpeechOutputHelper speechOutut;
    protected SpeechInputHelper speechInput;

    protected AccessibilityPreferences accessibilityPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.getInstance().applyTheme(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ThemeChanger.getInstance().applyTheme(this);

    }


    protected AccessibilityPreferences getAccessibilityPreferences() {

        if (accessibilityPreferences == null) {
            accessibilityPreferences = new AccessibilityPreferences(this);
        }
        return accessibilityPreferences;
    }


    protected SpeechOutputHelper getSpeechOutputHelper(String inittext) {

        if (speechOutut == null) {
            speechOutut = new SpeechOutputHelper(this, inittext);
        }
        return speechOutut;
    }

    protected SpeechOutputHelper getSpeechOutputHelper() {
        if (speechOutut == null) {
            speechOutut = new SpeechOutputHelper(this);
        }
        return speechOutut;
    }

    protected SpeechInputHelper getSpeechInput() {
        if (speechInput == null) {
            speechInput = new SpeechInputHelper(this);
        }
        return speechInput;

    }


    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SpeechInputHelper.SPEECH_INPUT_MODE: {
                // if (resultCode == 1 && null != data) {
                if (null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("SPEECH INPUT", result.get(0));


                    speechInput.getLastTextTarget().setText(result.get(0));
                }
                break;
            }
        }
    }




    @Override
    protected void onResume() {
        super.onResume();




        // if speech output
        if (getAccessibilityPreferences().isEnableSpeechOutput()) {
            if (screenName != null) {
                getSpeechOutputHelper().speaking(screenName);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // if speech output
        if (getAccessibilityPreferences().isEnableSpeechOutput()) {
            if (screenName != null || screenDescription != null) {
                getSpeechOutputHelper().init(screenName + ",  " + screenDescription);
            }
        }
    }


}
