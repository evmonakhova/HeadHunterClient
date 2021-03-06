package ru.hh.headhunterclient.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alena on 07.05.2017.
 */

public class HttpLoader extends AsyncTaskLoader<String> {

    private static final String TAG = HttpLoader.class.getSimpleName();

    private static final String BASE_URL = "https://api.hh.ru/vacancies";
    private static final String PER_PAGE = "20";

    private String mKeyword = "Android";
    private int mPage = 0;

    public HttpLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        HttpURLConnection urlConnection = null;
        String response;

        try {
            String encodedKeyword = URLEncoder.encode(mKeyword, "UTF-8");
            URI uri = buildURI(BASE_URL, encodedKeyword, mPage, PER_PAGE);
            Log.i(TAG, String.format("loadInBackground. path: %s", uri.toURL()));
            URL url = uri.toURL();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            response = readResponse(urlConnection);

        } catch (URISyntaxException uriEx) {
            Log.e(TAG, "URISyntaxException ", uriEx);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "IOException ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public void setPage(int page) {
        mPage = page;
    }

    private URI buildURI(String uri, String keyword, int page, String perPage)
            throws URISyntaxException {
        StringBuilder newUri = new StringBuilder(uri);
        newUri.append("?text=").append(keyword)
                .append("&page=").append(page)
                .append("&perPage=").append(perPage);
        return new URI(newUri.toString());
    }

    private String readResponse(HttpURLConnection urlConnection) {
        String line;
        BufferedReader reader = null;
        String jsonStr = null;

        try {
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                return null;
            }
            jsonStr = buffer.toString();

        } catch (IOException ioe) {
            Log.e(TAG, "IOException ", ioe);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return jsonStr;
    }

}
