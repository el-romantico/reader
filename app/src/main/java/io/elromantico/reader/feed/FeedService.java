package io.elromantico.reader.feed;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.FeedNarrator;
import io.elromantico.reader.feed.FeedEntities.Feed;
import io.elromantico.reader.feed.FeedEntities.FeedItem;

public class FeedService {
    private final FeedRepository feedRepository;

    public FeedService() {
        this.feedRepository = new FeedRepository();
    }

    public ArrayList<FeedNarrator.Item> getUnreadArticles() {
        List<FeedItem> unreadArticles = feedRepository.getUnreadArticles();
        ArrayList<FeedNarrator.Item> unreadContents = new ArrayList<>();
        for (int i = 0; i < unreadArticles.size(); i++) {
            unreadContents.add(new FeedNarrator.Item(
                    unreadArticles.get(i).title,
                    unreadArticles.get(i).summary,
                    unreadArticles.get(i).content));
        }
        return unreadContents;
    }

    public void MockData() {
        Feed feed = new Feed("Title", "unique_link", "education posts");
        feed.save();
        FeedItem item1 = new FeedItem(
                "Always Invest In Your Education",
                "How much did you invest in yourself recently?",
                "invest in yourself",
                "www.link.com/1",
                "George",
                "1");
        item1.save();
        FeedItem item2 = new FeedItem(
                "Vue 2.0 is Here!",
                "Vue 2.0 is Here! Today I am thrilled to announce the official release of Vue.js 2.0: Ghost in the Shell. After 8 alphas, 8 betas and 8 rcs (a total coincidence!), Vue.js 2.0 is ready for production! The official guide has been fully updated and is available at vuejs.org/guide.",
                "Vue 2.0 is Here!",
                "www.link.com/2",
                "George",
                "2");
        item2.save();
    }
}
