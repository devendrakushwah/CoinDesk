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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
