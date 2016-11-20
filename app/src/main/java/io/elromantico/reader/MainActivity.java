package io.elromantico.reader;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.feed.FeedService;
import io.elromantico.reader.feed.ParsedFeedItem;
import io.elromantico.reader.feed.ParsedFeedItemsAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int CALLBACK_CODE = 1337;

    private SpeechSynthesizer synthesizer;
    private FeedService feedService = new FeedService();

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
                final EditText feedUrl = new EditText(MainActivity.this);

                View viewInflated = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.insertion_dialog, (ViewGroup) MainActivity.this.findViewById(android.R.id.content), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.insertion_input_field);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add new feed source")
                        .setView(feedUrl)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                String url = feedUrl.getText().toString();
                                String url = "http://waitbutwhy.com/feed";
                                feedService.execute(url);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_items_recycler_view);

        ParsedFeedItemsAdapter mAdapter = new ParsedFeedItemsAdapter(feedService.getFeedChannels());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        List<FeedNarrator.Item> items = feedService.getUnreadArticles();
        synthesizer = new SpeechSynthesizer(this, new FeedNarrator(this, items));

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
