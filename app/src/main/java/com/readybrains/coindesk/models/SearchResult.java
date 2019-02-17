package com.readybrains.coindesk.models;

public class SearchResult {
    String name;
    String id;

    public SearchResult(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
