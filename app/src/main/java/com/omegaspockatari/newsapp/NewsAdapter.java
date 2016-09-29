package com.omegaspockatari.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ${Michael} on 8/24/2016.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_web_title);
        titleTextView.setText(currentNews.getNewsTitle());

        TextView publicationDateTextView = (TextView) listItemView.findViewById(R.id.news_web_publication_date);
        publicationDateTextView.setText(currentNews.getNewsPublicationDate());

        TextView typeTextView = (TextView) listItemView.findViewById(R.id.news_type);
        typeTextView.setText(currentNews.getNewsType());

        TextView sectionNameTextView = (TextView) listItemView.findViewById(R.id.news_section_name);
        sectionNameTextView.setText(currentNews.getNewsSectionName());

        TextView authorsTextView = (TextView) listItemView.findViewById(R.id.news_authors);
        if (!(currentNews.getNewsAuthors() == null)) {
            authorsTextView.setText(currentNews.getNewsAuthors());
        } else {
            authorsTextView.setText(getContext().getString(R.string.no_authors_listed));
        }
        return listItemView;
    }
}