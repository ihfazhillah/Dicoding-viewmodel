package com.ihfazh.dicodingviewmodel;

public class WeatherItem {
    private int id;
    private String name;
    private String currentWearther;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentWearther() {
        return currentWearther;
    }

    public void setCurrentWearther(String currentWearther) {
        this.currentWearther = currentWearther;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    private String temperature;
}
