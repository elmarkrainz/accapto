package org.accapto.accessibilitypatternlib;


import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by krajn on 11/09/17.
 */

public class AccaptoBaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        ThemeChanger.getInstance().changeTheme(R.style.AccaptoAppTheme_HicontrastTheme,this);
        ThemeChanger.getInstance().applyTheme(this);


    }
}
