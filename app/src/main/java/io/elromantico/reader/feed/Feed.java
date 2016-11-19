package io.elromantico.reader.feed;

import java.util.ArrayList;
import java.util.List;

public class Feed {

        final String title;
        final String description;
        final String pubDate;

        final List<FeedItem> entries = new ArrayList<FeedItem>();

        public Feed(String title, String description, String pubDate) {
                this.title = title;
                this.description = description;
                this.pubDate = pubDate;
        }

        public List<FeedItem> getMessages() {
                return entries;
        }

        public String getTitle() {
                return title;
        }

        public String getDescription() {
                return description;
        }

        public String getPubDate() {
                return pubDate;
        }

        @Override
        public String toString() {
                return "Feed [description=" + description + ", pubDate=" + pubDate + ", title=" + title + "]";
        }
}
