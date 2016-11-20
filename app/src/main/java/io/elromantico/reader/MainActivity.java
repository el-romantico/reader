package io.elromantico.reader;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.feed.FeedItem;
import io.elromantico.reader.feed.FeedItemsAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int CALLBACK_CODE = 1337;

    private SpeechSynthesizer synthesizer;
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

        List<FeedNarrator.Item> items = new ArrayList<>();
        items.add(new FeedNarrator.Item("Always Invest In Your Education", "How much did you invest in yourself recently?", ""));
        items.add(new FeedNarrator.Item("Vue 2.0 is Here!", "Today I am thrilled to announce the official release of Vue.js 2.0: Ghost in the Shell.", " After 8 alphas, 8 betas and 8 rcs (a total coincidence!), Vue.js 2.0 is ready for production! The official guide has been fully updated and is available at vuejs.org/guide."));
        items.add(new FeedNarrator.Item("We Gotta Fuckin Stop This", "Talk to any constitutional lawyer and they will explain to you how the Electoral College was put in place as a safeguard against the dangers of a purely representative democracy. The founding fathers cautioned against endowing absolute power of election to a population which can be…", ""));
        synthesizer = new SpeechSynthesizer(this, new FeedNarrator(this, items));

        setContentView(R.layout.main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                CALLBACK_CODE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        synthesizer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synthesizer.destroy();
    }
}
