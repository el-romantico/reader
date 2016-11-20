package io.elromantico.reader;

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

    private List<Item> items;

    public FeedNarrator(List<Item> items) {
        this.items = items;
    }

    @Override
    public void onInit(SpeechSynthesizer synth) {
        for (Item item : items) {
            synth.pronounce(item.title);
            synth.pronounce(item.summary);
        }
    }
}