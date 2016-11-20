package io.elromantico.reader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class SpeechSynthesizer {
    public interface OnInitListener {

        void onInit(SpeechSynthesizer synth);
        void onComplete();
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
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
            }

            @Override
            public void onDone(String utteranceId) {
                if (utteranceId == "last") {
                    listener.onComplete();
                }
            }

            @Override
            public void onError(String utteranceId) {
                if (utteranceId == "last") {
                    listener.onComplete();
                }
            }
        });
    }

    public void stop() {
        tts.stop();
    }

    public void destroy() {
        tts.shutdown();
        tts = null;
    }

    public void pronounce(final String text, boolean lastBatch) {
        if (tts != null) {
            String utteranceId = "interm";
            if (lastBatch) {
                utteranceId = "last";
            }
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, utteranceId);
        }
    }
}
