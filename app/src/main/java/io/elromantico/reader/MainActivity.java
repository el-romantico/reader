package io.elromantico.reader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.feed.FeedItem;
import io.elromantico.reader.feed.FeedItemsAdapter;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final int CALLBACK_CODE = 1337;

    private SpeechSynthesizer synth;
    private SpeechRecognizer sr;
    private List<FeedItem> feedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_items_recycler_view);

        FeedItemsAdapter mAdapter = new FeedItemsAdapter(feedItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        List<FeedNarrator.Item> items = new ArrayList<>();
        items.add(new FeedNarrator.Item("Always Invest In Your Education", "How much did you invest in yourself recently?", ""));
        items.add(new FeedNarrator.Item("Vue 2.0 is Here!", "Today I am thrilled to announce the official release of Vue.js 2.0: Ghost in the Shell. After 8 alphas, 8 betas and 8 rcs (a total coincidence!), Vue.js 2.0 is ready for production! The official guide has been fully updated and is available at vuejs.org/guide.", ""));
        items.add(new FeedNarrator.Item("We Gotta Fuckin Stop This", "Talk to any constitutional lawyer and they will explain to you how the Electoral College was put in place as a safeguard against the dangers of a purely representative democracy. The founding fathers cautioned against endowing absolute power of election to a population which can be…", ""));
        synth = new SpeechSynthesizer(this, new FeedNarrator(items));

        setContentView(R.layout.main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                CALLBACK_CODE);

        Button speakButton = (Button) findViewById(R.id.btn_speak);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new VoiceListener(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synth.destroy();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            sr.startListening(intent);
        }
    }
}
