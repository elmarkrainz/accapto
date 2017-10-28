package org.accapto.accessibilitypatternlib.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.EditText;
import android.widget.Toast;

import org.accapto.accessibilitypatternlib.R;

import java.util.Locale;

/**
 * Helper class to add voice input to a textview
 */
public class SpeechInputHelper {

    final public static int SPEECH_INPUT_MODE = 143;

    private Activity activityContext;

    private EditText textTarget;

    public SpeechInputHelper(Activity activity, EditText editText) {
        activityContext = activity;
        textTarget=editText;
    }

    public void startSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, activityContext.getString(R.string.speech_prompt));
        try {
            activityContext.startActivityForResult(intent, SPEECH_INPUT_MODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activityContext.getApplicationContext(), activityContext.getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public EditText getTextTarget() {
        return textTarget;
    }
}
