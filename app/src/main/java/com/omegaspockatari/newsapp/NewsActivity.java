package com.omegaspockatari.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = NewsActivity.class.getSimpleName();
    private static final String NEWS_API_URL = "http://content.guardianapis.com/search?q=magic%20leap&api-key=test&show-tags=contributor";
    /**
     * This is a constant value for our News Loader. Only really relevant if we have multiple
     */
    private static final int NEWS_LOADER_ID = 1;
    private TextView mEmptyStateView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        /** Reference to the list view within the layouts (@link activity_news) */
        ListView newsListView = (ListView) findViewById(R.id.list);

        /** This allows for our project to still have a UI should nothing come from JSON */
        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateView);

        /** Create a new adapter to take an empty list of news items */
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        /** Setup the adapter on our given list view to populate the UI. */
        newsListView.setAdapter(mAdapter);

        /** Get a reference to the ConnectivityManager to check state of network connectivity */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        /** Get details on the currently active default data network */
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        /** If there is a network connection, fetch data */
        if (networkInfo != null && networkInfo.isConnected()) {
            /**
             * Create a reference to LoaderManager in order to interact with available loaders
             *
             * Initialize loader. Given current id of loader we want, null for passed bundle
             * and the activity is passed for the LoaderCallbacks parameter. This is valid because
             * the activity implements the LoaderCallbacks interface.
             */
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                News currentNewsItem = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNewsItem.getNewsWebUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        /** Create a baseUri and then build upon it with relevant user data should it be integrated */
        Uri baseUri = Uri.parse(NEWS_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        /** Hide loading indicator because the data has been loaded */
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        /** Set empty state text to display "No News Articles Found." */
        mEmptyStateView.setText(R.string.no_news_found_edit_text);

        /** Clear the adapter of previously obtained News data */
        mAdapter.clear();

        /**
         * Should the provided list of news items be valid, update the adapter's data set.
         * This triggers the list view to update.
         */
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
