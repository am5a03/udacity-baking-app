package com.raymond.udacity.bakingapp.models.api;

public class ApiStep {
    public String videoURL;

    public String description;

    public int id;

    public String shortDescription;

    public String thumbnailURL;

    @Override
    public String toString() {
        return "ApiStep [videoURL = "+videoURL+", description = "+description+", id = "+id+", shortDescription = "+shortDescription+", thumbnailURL = "+thumbnailURL+"]";
    }
}
