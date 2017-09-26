package ${package};

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;
  
${imports}

public class ${activityname} extends AccaptoBaseActivity
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
