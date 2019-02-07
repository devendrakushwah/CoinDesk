package com.readybrains.coindesk.models;

public class TopCoins {
    int id;
    String symbol;
    String name;
    String image_url;
    String change_day;
    String price;

    public TopCoins(int id, String symbol, String name, String image_url, String change_day, String price){
        this.id=id;
        this.symbol=symbol;
        this.name=name;
        this.image_url=image_url;
        this.change_day=change_day;
        this.price=price;
    }

    public int getTd(){
        return id;
    }
    public String getSymbol(){
        return symbol;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getChangeDay() {
        return change_day;
    }

    public String getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public void setChangeDay(String change_day) {
        this.change_day = change_day;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
