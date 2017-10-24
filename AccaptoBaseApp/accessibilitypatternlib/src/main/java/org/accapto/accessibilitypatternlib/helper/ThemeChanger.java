package org.accapto.accessibilitypatternlib.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.accapto.accessibilitypatternlib.R;

/**
 * Changes Theme
 * Created by Elmar on 26/08/17.
 */

public class ThemeChanger {

    private static ThemeChanger instance = null;

    protected ThemeChanger() {

    }

    public static ThemeChanger getInstance() {
        if (instance == null) {
            instance = new ThemeChanger();
        }
        return instance;
    }


    public void applyTheme(Context context) {
        ThemeSaver pt = new ThemeSaver(context);
        context.setTheme(pt.getProfileTheme());
    }


    public void changeTheme(int styleId, Context context) {
        // profileTheme Helper
        ThemeSaver pt = new ThemeSaver(context);
        pt.setProfileTheme(styleId);

        // restart app
        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


    public void changeThemeGrey(Context context) {
        changeTheme(R.style.AccaptoAppTheme_blind, context);
    }


    public void changeThemeBlackYellow(Context context) {
        changeTheme(R.style.AccaptoAppTheme_defect, context);
    }


    public void changeThemeBasic(Context context) {
        changeTheme(R.style.AccaptoAppTheme, context);
    }


}
