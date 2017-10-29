package org.accapto.accessibilitypatternlib.helper;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import org.accapto.accessibilitypatternlib.AccaptoBaseActivity;

import java.util.Locale;

/**
 * Helper class for easy Text-to-speech output
 *
 * @author EKrainz
 */

public class SpeechOutputHelper {

    private final Context context;
    private  String initText=null;
    private Locale locale;
    private TextToSpeech t2s;


    public SpeechOutputHelper(Context c) {

        this.context =c;
        this.locale = getDeviceLocale();

       // init(c);
    }


    public SpeechOutputHelper(Context c, Locale l) {
        this.context =c;
        this.locale = l;

     //   init(c);
    }

    public SpeechOutputHelper(Context c, String initText) {

        this.context =c;
        this.locale = getDeviceLocale();


       // init(c);
    }






    public void init(String initText){
        this.initText = initText;
        init();

    }



    public void init(){//(Context c) {
        t2s = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t2s.setLanguage(SpeechOutputHelper.this.locale);

                    if (initText!= null){
                        t2s.speak(initText, TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private Locale getDeviceLocale(){
       return context.getResources().getConfiguration().locale;
    }

    /**
     * easy method for speech output
     * @param text text for speech
     */
    public void speaking(String text) {
        t2s.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

    }

    public void setLocale(Locale locale){
        this.locale = locale;
        t2s.setLanguage(locale);
    }


}
