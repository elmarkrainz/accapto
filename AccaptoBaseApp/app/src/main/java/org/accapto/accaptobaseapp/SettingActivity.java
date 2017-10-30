package org.accapto.accaptobaseapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;
import org.accapto.accessibilitypatternlib.helper.AccessibilityPreferences;

public class SettingActivity extends AccaptoBaseActivity {

    private static final int PROFILE_NORMAL = 1;
    private static final int PROFILE_BLIND = 2;
    private static final int PROFILE_LOWVISION = 3;
    private CheckBox checkBoxSpeechOutput;
    private CheckBox checkBoxSpeechInput;
    private CheckBox checkBoxHighContrast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.screenName = "einstellungen";
        this.screenDescription = "einstellungen für barrierefreiheit";

        checkBoxHighContrast = (CheckBox) findViewById(R.id.checkBoxHighContrast);
        checkBoxSpeechInput = (CheckBox) findViewById(R.id.checkBoxSpeechInput);
        checkBoxSpeechOutput = (CheckBox) findViewById(R.id.checkBoxSpeechOutput);

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonNormal:
                if (checked)
                    applyProfile(PROFILE_NORMAL);
                break;
            case R.id.radioButtonLowVision:
                if (checked)
                    applyProfile(PROFILE_LOWVISION);
                break;
            case R.id.radioButtonBlind:
                if (checked)
                    applyProfile(PROFILE_BLIND);
                break;
        }
    }

    private void applyProfile(int profile) {


        switch (profile) {
            case PROFILE_NORMAL:


                checkBoxHighContrast.setChecked(false);
                checkBoxSpeechInput.setChecked(false);
                checkBoxSpeechOutput.setChecked(false);
                break;

            case PROFILE_LOWVISION:
                checkBoxHighContrast.setChecked(true);
                checkBoxSpeechInput.setChecked(true);
                checkBoxSpeechOutput.setChecked(true);
                break;

            case PROFILE_BLIND:
                checkBoxHighContrast.setChecked(false);
                checkBoxSpeechInput.setChecked(false);
                checkBoxSpeechOutput.setChecked(true);
                break;
        }

    }


    public void applySettings(View v) {

        AccessibilityPreferences prefs= new AccessibilityPreferences(this);

        prefs.setEnableSpeechOutput(checkBoxSpeechOutput.isChecked());




    }
}
