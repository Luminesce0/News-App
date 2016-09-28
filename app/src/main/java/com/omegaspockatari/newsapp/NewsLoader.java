package com.omegaspockatari.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by ${Michael} on 8/24/2016.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}
     *
     * @param context
     * @param url
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.v(LOG_TAG, "NewsLoader Constructor");
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "forceLoad() onStartLoading()");
        forceLoad();
    }

    /**
     * Runs on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        Log.v(LOG_TAG, "loadInBackground() - mURL Null or list news = QueryUtisl.fetchNewsData");
        if (mUrl == null) {
            return null;
        }

        /** Call the QueryUtils list of commands and get it started and return the List of news
         * items relevant to the process.
         */
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
