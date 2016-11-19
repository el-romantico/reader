package io.elromantico.reader.rssfeedparser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RSSFeedParser {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LANGUAGE = "language";
    private static final String COPYRIGHT = "copyright";
    private static final String LINK = "link";
    private static final String AUTHOR = "author";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";

    private XmlPullParser parser;

    public RSSFeedParser(String feedUrl) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            this.parser = factory.newPullParser();
            URL url = new URL(feedUrl);
            setInputStream(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public Feed readFeed() {
        Feed feed = null;
        try {
            int event = parser.getEventType();

            boolean isFeedHeader = true;

            String description = "";
            String title = "";
            String author = "";
            String pubdate = "";
            String text = "";

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(name.equals(ITEM) && isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, description, pubdate);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case ITEM:
                                FeedMessage message = new FeedMessage();
                                message.setAuthor(author);
                                message.setDescription(description);
                                message.setTitle(title);
                                feed.entries.add(message);

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

    private void setInputStream(URL url) {
        try {
            parser.setInput(url.openStream(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
