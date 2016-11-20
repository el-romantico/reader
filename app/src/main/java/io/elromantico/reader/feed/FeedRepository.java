package io.elromantico.reader.feed;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.feed.FeedEntities.Feed;
import io.elromantico.reader.feed.FeedEntities.FeedItem;

public class FeedRepository {
    public List<FeedItem> getUnreadArticles() {
        return FeedItem.find(FeedItem.class, "is_Read = ?", "0");
    }

    public List<String> getFeeds() {
        List<Feed> allFeeds = Feed.listAll(Feed.class);
        ArrayList<String> feedNames = new ArrayList<>();
        for (Feed feed: allFeeds) {
            feedNames.add(feed.title);
        }
        return feedNames;
    }
}
