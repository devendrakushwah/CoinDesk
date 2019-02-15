package com.readybrains.coindesk.models;

public class Coins {
    String id;
    String rank;
    String symbol;
    String name;
    String price;
    String change_day;
    String image;

    public Coins(String id, String rank, String symbol, String name, String price, String change_day, String image) {
        this.id = id;
        this.rank = rank;
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.change_day = change_day;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getChange_day() {
        return change_day;
    }

    public String getImage() {
        return image;
    }
}
