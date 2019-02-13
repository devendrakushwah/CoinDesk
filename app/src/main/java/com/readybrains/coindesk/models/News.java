package com.readybrains.coindesk.models;

public class News {
    String title;
    String image;
    String url;
    String date;
    String source;

    public News(String title, String image, String url, String date, String source) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.date = date;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }
}
