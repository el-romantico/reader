package io.elromantico.reader.summarizer;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Summarizer {
    private static final AsyncHttpClient http;

    public interface ResponseHandler {
        void handle(String response);
    }

    static {
        http = new AsyncHttpClient();
        http.addHeader("X-Mashape-Key", "vIO3I6jt2kmshqXX4pR2bXfwe7bAp1HNpdrjsnG5YTslSV1IK1");
        http.addHeader("Content-Type", "application/json");
        http.addHeader("Accept", "application/json");
    }

    public void summarize(String text, final ResponseHandler handler) {
        try {
            JSONObject body = new JSONObject();
            body.put("Percent", "20");
            body.put("Language", "en");
            body.put("Text", text);

            http.post(null, "https://cotomax-summarizer-text-v1.p.mashape.com/summarizer",
                    new StringEntity(body.toString()),
                    "application/json",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            handler.handle(new String(responseBody));
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // TODO
                        }
                    });
        } catch (UnsupportedEncodingException | JSONException e) {
        }
    }
}
