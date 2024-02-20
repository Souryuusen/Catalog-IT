package com.soursoft.catalogit.valueobject.requests;

public class MovieScrapePostRequest {
    private String identifier;

    public MovieScrapePostRequest() {
    }

    public MovieScrapePostRequest(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
