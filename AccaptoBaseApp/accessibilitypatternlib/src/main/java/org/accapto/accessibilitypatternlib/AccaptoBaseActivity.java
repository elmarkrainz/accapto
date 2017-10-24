package org.accapto.accessibilitypatternlib;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.accapto.accessibilitypatternlib.helper.SpeechOutputHelper;
import org.accapto.accessibilitypatternlib.helper.ThemeChanger;

/**
 * Created by ekrainz on 11/09/17.
 */

public abstract class AccaptoBaseActivity extends AppCompatActivity {


    private SpeechOutputHelper voicer;


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


}
