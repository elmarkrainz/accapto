package org.accapto.accaptobaseapp;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;
import org.accapto.accessibilitypatternlib.helper.ThemeChanger;

public class MainActivity extends AccaptoBaseActivity {

    private String screen_name = "Start";
    private String screen_desc = "demonstration of Accessibility features";

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        screenName = "Start";
        screenDescription = "demonstration of Accessibility features";


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
                ThemeChanger.getInstance().changeThemeBasic(a);


            }
        });

        CheckBox checkBoxspeechinput = (CheckBox) findViewById(R.id.checkBoxspeechinput);
        checkBoxspeechinput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ToggleSpeechInput(isChecked);

            }
        });


        EditText edt = (EditText) findViewById(R.id.editText);


    }


    private void ToggleSpeechInput(boolean isChecked) {

        TextView txt = (TextView) findViewById(R.id.textView);
        EditText edt = (EditText) findViewById(R.id.editText);
        EditText edt2 = (EditText) findViewById(R.id.editText2);


        if (isChecked) {
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    MainActivity.this.getSpeechInput().startSpeechInput();
                    MainActivity.this.getSpeechInput().startSpeechInput((TextView) v);
                }
            });

            edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.getSpeechInput().startSpeechInput((TextView) v);

                }
            });

            edt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.getSpeechInput().startSpeechInput((TextView) v);

                }
            });

        } else {

            edt.setOnClickListener(null);
            edt2.setOnClickListener(null);
            txt.setOnClickListener(null);

        }
    }

    
    public void openSettings(View v) {
        startActivity(new Intent(this, SettingActivity.class));
    }
}
