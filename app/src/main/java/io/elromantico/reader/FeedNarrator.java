package io.elromantico.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FeedNarrator implements SpeechSynthesizer.OnInitListener {
    public static class Item {
        public String title;
        public String summary;
        public String content;

        public Item(String title, String summary, String content) {
            this.title = title;
            this.summary = summary;
            this.content = content;
        }
    }

    private SpeechRecognizer recognizer;
    private Activity activity;
    private List<Item> items;
    private int idx;

    public FeedNarrator(Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;
        idx = 0;
        recognizer = SpeechRecognizer.createSpeechRecognizer(activity);
    }

    @Override
    public void onInit(final SpeechSynthesizer synthesizer) {
        readItem(synthesizer);
        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
            }

            @Override
            public void onResults(Bundle results) {
                List<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String speech = TextUtils.join(" ", data);
                Log.i("speech", speech);

                if (speech.contains("next")) {
                    idx++;

                    pause(500);
                    readItem(synthesizer);
                } else if (speech.contains("details")) {
                    pause(500);
                    readDetails(synthesizer);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }

            private void pause(long millis) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        });
    }

    @Override
    public void onComplete() {
        command();
    }

    private void readItem(SpeechSynthesizer synthesizer) {
        if (items.size() <= idx) {
            return;
        }

        Item item = items.get(idx);
        synthesizer.pronounce(item.title + "\n" + item.summary);
    }

    private void readDetails(SpeechSynthesizer synthesizer) {
        if (items.size() <= idx) {
            return;
        }

        Item item = items.get(idx);

        String[] batches = item.content.split("[\\.?!,]");
        for (String batch : batches) {
            synthesizer.pronounce(item.content);
        }
    }

    private void command() {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
                recognizer.startListening(intent);
            }
        });
    }
}