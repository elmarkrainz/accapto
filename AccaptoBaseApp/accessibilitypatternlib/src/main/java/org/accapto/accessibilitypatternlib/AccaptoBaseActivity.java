package org.accapto.accessibilitypatternlib;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import org.accapto.accessibilitypatternlib.helper.SpeechInputHelper;
import org.accapto.accessibilitypatternlib.helper.SpeechOutputHelper;
import org.accapto.accessibilitypatternlib.helper.ThemeChanger;

import java.util.ArrayList;

/**
 * Created by ekrainz on 11/09/17.
 */

public abstract class AccaptoBaseActivity extends AppCompatActivity {


    private SpeechOutputHelper voicer;
    private SpeechInputHelper speechInput;


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

    protected SpeechOutputHelper getSpeechOutputHelper() {
        if (voicer == null) {
            new SpeechOutputHelper(this);
        }
        return voicer;
    }

    protected SpeechInputHelper getSpeechInput() {

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
                    speechInput.getTextTarget().setText(result.get(0));
                }
                break;
            }
        }
    }


    public void initSpeechInput(EditText editText) {
        if (speechInput == null) {
            speechInput= new SpeechInputHelper(this, editText);
        }

    }
}
