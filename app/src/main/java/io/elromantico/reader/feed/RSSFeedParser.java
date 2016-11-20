package io.elromantico.reader.feed;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.client.ClientProtocolException;

public class RSSFeedParser extends AsyncTask<String, Void, ParsedFeed> {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String AUTHOR = "author";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String LINK = "link";

    private String feedUrl = "";

    private XmlPullParser parser;

    public RSSFeedParser(String feedUrl) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            this.feedUrl = feedUrl;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ParsedFeed readFeed() {
        ParsedFeed feed = null;
        try {
            int event = parser.getEventType();

            boolean isFeedHeader = true;

            String description = "";
            String title = "";
            String author = "";
            String pubdate = "";
            String text = "";
            String link = "";

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals(ITEM) && isFeedHeader) {
                            isFeedHeader = false;
                            feed = new ParsedFeed(title, description, pubdate, link);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case ITEM:
                                ParsedFeedItem message = new ParsedFeedItem();
                                message.setAuthor(author);
                                message.setDescription(description);
                                message.setTitle(title);
                                feed.getMessages().add(message);

                            case TITLE:
                                title = text;
                                break;
                            case DESCRIPTION:
                                description = text;
                                break;
                            case AUTHOR:
                                author = text;
                                break;
                            case PUB_DATE:
                                pubdate = text;
                                break;
                            case LINK:
                                link = text;
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (IOException | XmlPullParserException e1) {
            e1.printStackTrace();
        }
        return feed;
    }

    @Override
    protected ParsedFeed doInBackground(String... strings) {
        InputStream stream = null;
        try {
            URL url = new URL(this.feedUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            stream = connection.getInputStream();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            parser.setInput(stream, null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return readFeed();
    }
}
