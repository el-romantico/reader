package io.elromantico.reader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class SpeechSynthesizer {
    public interface OnInitListener {

        void onInit(SpeechSynthesizer synth);
        void onComplete();
    }

    private TextToSpeech tts;
    private AtomicInteger active;

    public SpeechSynthesizer(Context context, final OnInitListener listener) {
        active = new AtomicInteger(0);

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
                int a = active.incrementAndGet();
                Log.i("active+", String.valueOf(a));
            }

            @Override
            public void onDone(String utteranceId) {
                int a = active.decrementAndGet();
                Log.i("active-", String.valueOf(a));

                if (a == 0) {
                    listener.onComplete();
                }
            }

            @Override
            public void onError(String utteranceId) {
                int a = active.decrementAndGet();
                Log.i("active-", String.valueOf(a));

                if (a == 0) {
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

    public void pronounce(final String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, "uttr");
        }
    }
}
