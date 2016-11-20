package io.elromantico.reader.feed;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.elromantico.reader.FeedNarrator;
import io.elromantico.reader.feed.FeedEntities.Feed;
import io.elromantico.reader.feed.FeedEntities.FeedItem;
import io.elromantico.reader.summarizer.Summarizer;

public class FeedService extends AsyncTask<String, Void, Void> {
    private final FeedRepository feedRepository;
    private final Summarizer summarizer;

    public FeedService() {
        this.feedRepository = new FeedRepository();
        this.summarizer = new Summarizer();
    }

    @Override
    protected Void doInBackground(String... params) {
        RSSFeedParser parser = new RSSFeedParser(params[0]);
        ParsedFeed feed = parser.doInBackground();

        Feed newFeedChannel = new Feed(feed.title,feed.link, feed.description);
        newFeedChannel.save();
        for (ParsedFeedItem feedItem: feed.getMessages()) {
            Document doc = Jsoup.parse(feedItem.getDescription());
            String text = doc.body().text();
            String summary = summarizer.summarize(text);
            FeedItem newItem = new FeedItem(feedItem.title, feedItem.description, summary, feedItem.link, feedItem.author, feedItem.guid);
            newItem.feed = newFeedChannel;
            newItem.save();
        }

        return null;
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

    public List<String> getFeedChannels() {
        return feedRepository.getFeeds();
    }
}
