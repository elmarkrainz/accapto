package org.accapto.accaptobaseapp;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;
import org.accapto.accessibilitypatternlib.helper.SpeechOutputHelper;
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

        if (isChecked) {
            EditText edt = (EditText) findViewById(R.id.editText);
         //   this.initSpeechInput(edt);
           // this.getSpeechInput().addTextTarget(edt);


            TextView txt = (TextView) findViewById(R.id.textView);
            this.initSpeechInput(txt);

            this.getSpeechInput().addTextTarget(edt);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.getSpeechInput().startSpeechInput();
                }
            });

            edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity.this.getSpeechInput().startSpeechInput();
                }
            });
        } else {
            EditText edt = (EditText) findViewById(R.id.editText);
            edt.setOnClickListener(null);

        }
    }







    public void openSettings(View v) {
        startActivity(new Intent(this, SettingActivity.class));
    }
}
