package io.elromantico.reader.feed;

import java.util.ArrayList;
import java.util.List;

public class ParsedFeed {

        final String title;
        final String description;
        final String link;

        final List<ParsedFeedItem> entries = new ArrayList<ParsedFeedItem>();

        public ParsedFeed(String title, String description, String pubDate, String link) {
                this.title = title;
                this.description = description;
                this.link = link;
        }

        public List<ParsedFeedItem> getMessages() {
                return entries;
        }

        public String getTitle() {
                return title;
        }

        public String getDescription() {
                return description;
        }

        public String getLink() {
                return link;
        }

        @Override
        public String toString() {
                return "ParsedFeed [description=" + description + ", title=" + title + "]";
        }
}
