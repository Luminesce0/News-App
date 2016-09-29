package com.omegaspockatari.newsapp;

/**
 * Created by ${Michael} on 8/24/2016.
 */
public class News {

    private String newsAuthors;
    private String newsTitle;
    private String newsPublicationDate;
    private String newsType;
    private String newsSectionName;
    private String newsWebUrl;

    public News(String newsTitle, String newsPublicationDate, String newsType, String newsSectionName, String newsWebUrl, String newsAuthors) {
        this.newsTitle = newsTitle;
        this.newsPublicationDate = newsPublicationDate;
        this.newsType = newsType;
        this.newsSectionName = newsSectionName;
        this.newsWebUrl = newsWebUrl;
        this.newsAuthors = newsAuthors;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsPublicationDate() {
        return newsPublicationDate;
    }

    public String getNewsType() {
        return newsType;
    }

    public String getNewsSectionName() {
        return newsSectionName;
    }

    public String getNewsWebUrl() {
        return newsWebUrl;
    }

    public String getNewsAuthors() {
        return newsAuthors;
    }
}