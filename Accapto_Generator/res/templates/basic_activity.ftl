package ${package};

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;



import org.accapto.accessibility.A12ySettingsActivity;
import org.accapto.accessibility.ProfileBaseActivity;
  
${imports}

public class ${activityname} extends ProfileBaseActivity
{
	${variables}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${activity_layout});
        ${onCreate}
    }
    
    ${methods}
}
