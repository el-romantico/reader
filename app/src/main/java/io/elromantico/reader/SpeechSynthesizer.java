package io.elromantico.reader;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class SpeechSynthesizer {
    private static final AsyncHttpClient http = new AsyncHttpClient();

    private static final String EMAIL = "angelvenchev@yahoo.com";
    private static final String ACCOUNT_ID = "1f315ebe2f";
    private static final String LOGIN_PASSWORD = "32657db2a09d2b4b42a6";

    private Context context;

    public SpeechSynthesizer(Context context) {
        this.context = context;
    }

    public void pronounce(final String text) {
        try {
            sendRequest(text);
        } catch (Exception e) {
        }
    }

    private void sendRequest(final String text) throws Exception {
        RequestParams params = new RequestParams();
        params.add("method", "ConvertSimple");
        params.add("email", EMAIL);
        params.add("accountId", ACCOUNT_ID);
        params.add("loginKey", "LoginKey");
        params.add("loginPassword", LOGIN_PASSWORD);
        params.add("voice", "TTS_PAUL_DB");
        params.add("outputFormat", "FORMAT_WAV");
        params.add("sampleRate", "16");
        params.add("text", text);
        http.post("http://tts.neospeech.com/rest_1_1.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);

                Pattern p = Pattern.compile("conversionNumber=\\\"(.*?)\\\"", Pattern.DOTALL);
                Matcher m = p.matcher(body);
                if (m.find()) {
                    String id = m.group(1);
                    checkStatus(id);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO: Handle error.
            }
        });
    }

    private void checkStatus(final String id) {
        RequestParams params = new RequestParams();
        params.add("method", "GetConversionStatus");
        params.add("email", EMAIL);
        params.add("accountId", ACCOUNT_ID);
        params.add("conversionNumber", id);
        http.post("http://tts.neospeech.com/rest_1_1.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);

                Pattern p = Pattern.compile("downloadUrl=\\\"(.*?)\\\"", Pattern.DOTALL);
                Matcher m = p.matcher(body);
                if (m.find()) {
                    String url = m.group(1);

                    if (!url.isEmpty()) {
                        try {
                            MediaPlayer player = new MediaPlayer();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            player.setDataSource(context, Uri.parse(url));
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer player) {
                                    player.start();
                                }
                            });
                            player.prepareAsync();
                        } catch(Exception e) {
                            // TODO: Handle error.
                        }
                    } else {
                        checkStatus(id);
                    }
                } else {
                    checkStatus(id);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String body = new String(responseBody);

                Pattern p = Pattern.compile("conversionNumber=\\\"(.*?)\\\"", Pattern.DOTALL);
                Matcher m = p.matcher(body);
                if (m.find()) {
                    String id = m.group(1);
                    checkStatus(id);
                }
            }
        });
    }
}
