package io.elromantico.reader.feed;

import java.util.List;

import io.elromantico.reader.feed.FeedEntities.FeedItem;

public class FeedRepository {
    public List<FeedItem> getUnreadArticles() {
        return FeedItem.find(FeedItem.class, "is_Read = ?", "0");
    }
}
