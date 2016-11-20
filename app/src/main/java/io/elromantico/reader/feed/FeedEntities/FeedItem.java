package io.elromantico.reader.feed.FeedEntities;

import com.orm.SugarRecord;

/**
 * Created by angel on 11/20/2016.
 */

public class FeedItem extends SugarRecord{
    public String title;
    public String content;
    public String summary;
    public String link;
    public String author;
    public String guid;
    public boolean isRead;

    public Feed feed;

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
