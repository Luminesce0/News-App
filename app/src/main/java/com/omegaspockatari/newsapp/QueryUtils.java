package com.omegaspockatari.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Michael} on 8/24/2016.
 */
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Class holds static methods and variables. These are accessible directly from the class name.
     */
    public QueryUtils() {


    }

    public static List<News> fetchNewsData(String requestUrl) {

        /** Create URL object */
        URL url = createUrl(requestUrl);

        /** Perform HTTP request to the URL and receive a JSON response back. */
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP Request.", e);
        }

        /** Extract relevant fields from the JSON Response and create a list of news items through parsing */
        List<News> news = extractFeatureFromJson(jsonResponse);

        /** Return list of news items */
        return news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /** Milliseconds */);
            urlConnection.setConnectTimeout(15000 /** Milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns URL Object from the given stringUrl
     *
     * @param stringUrl
     * @return
     */

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem Constructing URL Object", e);
        }

        return url;

    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {

        /** If the JSON string is empty or null return early */
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {

            // Create a JSONObject out of the response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            JSONObject baseObject = baseJsonResponse.getJSONObject("response");

            // Extract JSONArray associated with the results of the search.
            JSONArray newsArray = baseObject.getJSONArray("results");

            // For each result create a {@link News} object.
        for (int i = 0; i < newsArray.length(); i++) {
            //Obtains the current news at location i of the array
            JSONObject currentNews = newsArray.getJSONObject(i);

            /** Gather relevant information from the result object in newsArray */
            String newsTitle = currentNews.optString("webTitle");
            String newsPublicationDate = currentNews.optString("webPublicationDate");
            String newsType = currentNews.optString("type");
            String newsSectionName = currentNews.optString("sectionName");
            String newsWebUrl = currentNews.optString("webUrl");

            // Create an {@link News} object with the obtained data
            News newsObject = new News(newsTitle, newsPublicationDate, newsType, newsSectionName, newsWebUrl);

            news.add(newsObject);
        }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem Parsing the News JSON Results.", e);
        }

        /** Return the list of News results */
        return news;
    }

}
