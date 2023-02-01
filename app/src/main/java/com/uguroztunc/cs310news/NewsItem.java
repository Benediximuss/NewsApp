package com.uguroztunc.cs310news;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class NewsItem implements Serializable {
    private int newsID;
    private String newsTitle;
    private String newsText;
    private String newsDate;
    private String newsImgPath;
    private String newsCategoryName;

    public NewsItem() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NewsItem(int newsID, String newsTitle, String newsText, String newsDate, String newsImgPath, String newsCategoryName) {
        this.newsID = newsID;
        this.newsTitle = newsTitle;
        this.newsText = newsText;

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").withZone(ZoneOffset.UTC);
        LocalDateTime dateTime = LocalDateTime.parse(newsDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.newsDate = outputFormatter.format(dateTime);

        this.newsImgPath = newsImgPath;
        this.newsCategoryName = newsCategoryName;
    }


    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsImgPath() {
        return newsImgPath;
    }

    public void setNewsImgPath(String newsImgPath) {
        this.newsImgPath = newsImgPath;
    }

    public String getNewsCategoryName() {
        return newsCategoryName;
    }

    public void setNewsCategoryName(String newsCategoryName) {
        this.newsCategoryName = newsCategoryName;
    }
}
