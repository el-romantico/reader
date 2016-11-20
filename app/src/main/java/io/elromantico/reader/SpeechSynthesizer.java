package io.elromantico.reader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;

import java.util.Locale;
import java.util.Set;

public class SpeechSynthesizer {
    public interface OnInitListener {

        void onInit();
    }

    private TextToSpeech tts;

    public SpeechSynthesizer(Context context, final OnInitListener listener) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
                listener.onInit();
            }
        });
    }

    public void destroy() {
        tts.stop();
        tts.shutdown();
    }

    public void pronounce(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }
}
