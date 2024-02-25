package com.soursoft.catalogit.dto;

import java.util.*;

public class MovieDataDTO {

    private final int STARTING_CAPACITY = 8;

    private String movieIdentifier;
    private String title;
    private String originalTitle;
    private String runtime;
    private String language;
    private String releaseDate;
    private String countryOfOrigin;
    private Set<String> directorsSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> writersSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> genresSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> starActorsSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> keywordsSet = new TreeSet<>();
    private Set<String> coverUrlsSet = new HashSet<>(STARTING_CAPACITY);
    private Map<String, String> productionDetailsMap = new HashMap<>();

    public MovieDataDTO() {}

    public MovieDataDTO(String title, String originalTitle) {
        this.title = title;
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Set<String> getDirectorsSet() {
        return directorsSet;
    }

    public void setDirectorsSet(Set<String> directorsSet) {
        this.directorsSet = directorsSet;
    }

    public Set<String> getWritersSet() {
        return writersSet;
    }

    public void setWritersSet(Set<String> writersSet) {
        this.writersSet = writersSet;
    }

    public Set<String> getGenresSet() {
        return genresSet;
    }

    public void setGenresSet(Set<String> genresSet) {
        this.genresSet = genresSet;
    }

    public Set<String> getStarActorsSet() {
        return starActorsSet;
    }

    public void setStarActorsSet(Set<String> starActorsSet) {
        this.starActorsSet = starActorsSet;
    }

    public Set<String> getKeywordsSet() {
        return keywordsSet;
    }

    public void setKeywordsSet(Set<String> keywordsSet) {
        this.keywordsSet = keywordsSet;
    }

    public Set<String> getCoverUrlsSet() {
        return coverUrlsSet;
    }

    public void setCoverUrlsSet(Set<String> coverUrlsSet) {
        this.coverUrlsSet = coverUrlsSet;
    }

    public Map<String, String> getProductionDetailsMap() {
        return productionDetailsMap;
    }

    public void setProductionDetailsMap(Map<String, String> productionDetailsMap) {
        this.productionDetailsMap = productionDetailsMap;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getMovieIdentifier() {
        return movieIdentifier;
    }

    public void setMovieIdentifier(String movieIdentifier) {
        this.movieIdentifier = movieIdentifier;
    }
}
