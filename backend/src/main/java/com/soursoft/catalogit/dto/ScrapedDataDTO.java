package com.soursoft.catalogit.dto;

import org.antlr.v4.runtime.tree.Tree;

import java.util.*;

public class ScrapedDataDTO {

    private final int STARTING_CAPACITY = 8;

    private String title;
    private String originalTitle;
    private Set<String> directorsSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> writersSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> genresSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> starActorsSet = new HashSet<>(STARTING_CAPACITY);
    private Set<String> keywordsSet = new TreeSet<>();
    private Set<String> coverUrlsSet = new HashSet<>(STARTING_CAPACITY);
    private Map<String, TreeSet<String>> productionDetailsMap = new HashMap<>();

    public ScrapedDataDTO() {}

    public ScrapedDataDTO(String title, String originalTitle) {
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

    public Map<String, TreeSet<String>> getProductionDetailsMap() {
        return productionDetailsMap;
    }

    public void setProductionDetailsMap(Map<String, TreeSet<String>> productionDetailsMap) {
        this.productionDetailsMap = productionDetailsMap;
    }
}
