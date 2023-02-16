package com.cp.project_food;

public class Food_Item
{
    private final String name;
    private final String date;
    private final String url;
    public Food_Item(String name, String url, String date) {
        this.name = name;
        this.date = date;
        this.url = url;
    }    public String getName() {
        return name;
    }    public String getUrl() {
        return url;
    }    public String getDate() {
        return date;
    }
}

