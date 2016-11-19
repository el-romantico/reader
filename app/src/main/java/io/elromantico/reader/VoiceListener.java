package io.elromantico.reader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class VoiceListener implements RecognitionListener {
    private static final String TAG = "MyStt3Activity";
    private TextView mText;
    private TextView command_text;

    public VoiceListener(Activity parentActivity) {
        mText = (TextView) parentActivity.findViewById(R.id.textView1);
        command_text = (TextView) parentActivity.findViewById(R.id.command_text);
    }

    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG, "onReadyForSpeech");
    }

    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech");
    }

    public void onRmsChanged(float rmsdB) {
        Log.d(TAG, "onRmsChanged");
    }

    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG, "onBufferReceived");
    }

    public void onEndOfSpeech() {
        Log.d(TAG, "onEndofSpeech");
    }

    public void onError(int error) {
        Log.d(TAG, "error " + error);
        mText.setText("error " + error);
    }

    public void onResults(Bundle results) {
        String str = new String();
        Log.d(TAG, "onResults " + results);
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        // Control structure for voice commands
        if (data.get(0).toString().contains("next")) {
            command_text.setText("OTKRIH: " + String.valueOf(data.get(0)));
        } else if (data.get(0).toString().contains("details")) {
            command_text.setText("OTKRIH: " + String.valueOf(data.get(0)));
        }

        for (int i = 0; i < data.size(); i++) {
            Log.d(TAG, "result " + data.get(i));
            str += data.get(i);
        }
        mText.setText("results: " + String.valueOf(data));
    }

    public void onPartialResults(Bundle partialResults) {
        Log.d(TAG, "onPartialResults");
    }

    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG, "onEvent " + eventType);
    }
}