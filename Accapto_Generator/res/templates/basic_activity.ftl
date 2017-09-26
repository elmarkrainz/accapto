package ${package};

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


import org.accapto.accessibility.A12ySettingsActivity;
import org.accapto.accessibility.ProfileBaseActivity;
  
${imports}

public class ${activityname} extends ProfileBaseActivity
{
	${variables}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${activity_layout});
        ${onCreate}
    }
    
    ${methods}
}
