package io.elromantico.reader.feed.FeedEntities;

/**
 * Created by angel on 11/20/2016.
 */

public class FeedItem {
    String title;
    String content;
    String summary;
    String link;
    String author;
    String guid;
    boolean isRead;

    Feed feed;

    public FeedItem() {
    }

    public FeedItem(String title, String description, String summary, String link, String author, String guid) {
        this.title = title;
        this.content = description;
        this.summary = summary;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.isRead = false;
    }
}
