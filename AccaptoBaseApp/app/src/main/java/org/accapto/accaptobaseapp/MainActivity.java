package org.accapto.accaptobaseapp;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;
import org.accapto.accessibilitypatternlib.helper.ThemeChanger;

public class MainActivity extends AccaptoBaseActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        btn = (Button) findViewById(R.id.button);
        CheckBox chj = (CheckBox) findViewById(R.id.checkBox);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a = MainActivity.this;
                ThemeChanger.getInstance().changeTheme(R.style.AccaptoAppTheme_defect, a);

            }
        });





        chj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a = MainActivity.this;
                ThemeChanger.getInstance().changeThemeBasic( a);


            }
        });


    }
}