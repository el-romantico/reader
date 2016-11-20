package io.elromantico.reader.feed.FeedEntities;

import com.orm.SugarRecord;

public class Feed extends SugarRecord {
    public String title;
    public String description;
    public String link;

    public Feed() {
    }

    public Feed(String title, String link, String description) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
}
