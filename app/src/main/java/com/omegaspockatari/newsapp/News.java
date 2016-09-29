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

    public News(String newsTitle, String newsPublicationDate, String newsType, String newsSectionName, String newsWebUrl, String newsAuthor) {
        this.newsTitle = newsTitle;
        this.newsPublicationDate = newsPublicationDate;
        this.newsType = newsType;
        this.newsSectionName = newsSectionName;
        this.newsWebUrl = newsWebUrl;
        this.newsAuthor = newsAuthor;
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
    
    public String getNewsAuthors(Boolean hasAuthors) {
        if (hasAuthors) {
        return newsAuthor;
    } else {
        return "No Authors...";    
    }
    
    public Boolean hasNewsAuthors(Boolean value) {
        return value;
    }
}
