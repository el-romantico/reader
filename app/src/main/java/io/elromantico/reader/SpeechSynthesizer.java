package io.elromantico.reader;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SpeechSynthesizer {
    public interface OnInitListener {

        void onInit(SpeechSynthesizer synth);
    }

    private TextToSpeech tts;

    public SpeechSynthesizer(Context context, final OnInitListener listener) {
        final SpeechSynthesizer synth = this;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
                listener.onInit(synth);
            }
        });
    }

    public void destroy() {
        tts.stop();
        tts.shutdown();
        tts = null;
    }

    public void pronounce(final String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        }
    }
}
