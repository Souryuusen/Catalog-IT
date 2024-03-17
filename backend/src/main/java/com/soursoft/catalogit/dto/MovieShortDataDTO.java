package com.soursoft.catalogit.dto;

import com.soursoft.catalogit.entity.Movie;

public class MovieShortDataDTO {

    private long movieId;
    private String title;
    private String originalTitle;
    private String runtime;
    private String countryOfOrigin;
    private String language;
    private String releaseDate;
    private String cover;

    public MovieShortDataDTO() {

    }

    public MovieShortDataDTO(Long movieId, String title, String originalTitle, String runtime,
                             String language, String releaseDate, String countryOfOrigin, String cover) {
        this.movieId = movieId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.runtime = runtime;
        this.language = language;
        this.releaseDate = releaseDate;
        this.countryOfOrigin = countryOfOrigin;
        this.cover = cover;
    }

    public MovieShortDataDTO(Movie movie) {
        setMovieId(movie.getMovieId());
        setTitle(movie.getTitle());
        setLanguage(movie.getTitle());
        setOriginalTitle(movie.getOriginalTitle());
        setRuntime(movie.getRuntime());
        setReleaseDate(movie.getReleaseDate());
        setCountryOfOrigin(movie.getCountryOfOrigin());
        setCover(movie.getCovers().iterator().next());
    }

    public MovieShortDataDTO(MovieDataDTO data) {
        setMovieId(0);
        setTitle(data.getTitle());
        setLanguage(data.getTitle());
        setOriginalTitle(data.getOriginalTitle());
        setRuntime(data.getRuntime());
        setReleaseDate(data.getReleaseDate());
        setCountryOfOrigin(data.getCountryOfOrigin());
        setCover(data.getCoverUrlsSet().iterator().next());
    }

    public static MovieShortDataDTO from(Movie movie) {
        return new MovieShortDataDTO(movie);
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
