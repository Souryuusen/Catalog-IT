package com.soursoft.catalogit.entity;

import com.soursoft.catalogit.dto.ScrapedDataDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private long movieId;

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "movie_title", nullable = false)
    private String title;

    @Length(min = 0, max = 255)
    @Column(name = "movie_original_title")
    private String originalTitle;

    @Length(min = 1, max = 20)
    @Column(name = "movie_runtime")
    private String runtime;

    @Length(min = 1, max = 255)
    @Column(name = "movie_country")
    private String countryOfOrigin;

    @Length(min = 1, max = 255)
    @Column(name = "movie_language")
    private String language;

    @Length(min = 1, max = 255)
    @Column(name = "movie_release_date")
    private String releaseDate;

    @NaturalId
    @NotNull(message = "Provided movie identifier cannot be null.")
    @Length(min = 1, max = 64, message = "Provided movie identifier should have length between {min} and {max}.")
    @Pattern(regexp = "(?i)^tt[0-9]{1,62}$", message = "Provided movie identifier should match {regexp}")
    @Column(name = "movie_identifier", nullable = false, unique = true)
    private String movieIdentifier;

    @ElementCollection
    private Set<String> directors;

    @ElementCollection
    private Set<String> writers;

    @ElementCollection
    private Set<String> genres;

    @ElementCollection
    private Set<String> keywords;

    @ElementCollection
    private Set<String> stars;

    @ElementCollection
    private Set<String> covers;

    public Movie() {
        this.movieIdentifier = "";
        this.title = "";
        this.originalTitle = "";
        this.countryOfOrigin = "";
        this.language = "";
        this.runtime = "";
        this.directors = new TreeSet<>();
        this.writers = new TreeSet<>();
        this.genres = new TreeSet<>();
        this.keywords = new TreeSet<>();
        this.stars = new TreeSet<>();
        this.covers = new HashSet<>();
    }

    public Movie(String title, String originalTitle, String runtime,
                    String countryOfOrigin, String language, Set<String> directors,
                        Set<String> writers, Set<String> genres, Set<String> keywords,
                            Set<String> stars, Set<String> covers, String movieIdentifier) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.runtime = runtime;
        this.countryOfOrigin = countryOfOrigin;
        this.language = language;
        this.directors = directors;
        this.writers = writers;
        this.genres = genres;
        this.keywords = keywords;
        this.stars = stars;
        this.covers = covers;
        this.movieIdentifier = movieIdentifier;
    }

    public Movie(Movie movie) {

    }

    public Movie(ScrapedDataDTO scrapedData) {
        this.movieIdentifier = scrapedData.getMovieIdentifier();
        this.title = scrapedData.getTitle();
        this.originalTitle = scrapedData.getOriginalTitle();
        this.releaseDate = scrapedData.getReleaseDate();
        this.runtime = scrapedData.getRuntime();
        this.language = scrapedData.getLanguage();
        this.countryOfOrigin = scrapedData.getCountry();
        this.directors = scrapedData.getDirectorsSet();
        this.writers = scrapedData.getWritersSet();
        this.genres = scrapedData.getGenresSet();
        this.keywords = scrapedData.getKeywordsSet();
        this.stars = scrapedData.getStarActorsSet();
        this.covers = scrapedData.getCoverUrlsSet();
    }

    public long getMovieId() {
        return movieId;
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

    public Set<String> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<String> directors) {
        this.directors = directors;
    }

    public Set<String> getWriters() {
        return writers;
    }

    public void setWriters(Set<String> writers) {
        this.writers = writers;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getStars() {
        return stars;
    }

    public void setStars(Set<String> stars) {
        this.stars = stars;
    }

    public Set<String> getCovers() {
        return covers;
    }

    public void setCovers(Set<String> covers) {
        this.covers = covers;
    }

    public String getMovieIdentifier() {
        return movieIdentifier;
    }

    public void setMovieIdentifier(String movieIdentifier) {
        this.movieIdentifier = movieIdentifier;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getMovieIdentifier(), movie.getMovieIdentifier()) && Objects.equals(getTitle(), movie.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieIdentifier(), getTitle());
    }

    public static class MovieBuilder {

        private Movie buildedMovie;

        private Movie getBuildedMovie() {
            return this.buildedMovie;
        }

        public MovieBuilder reset() {
            this.buildedMovie = new Movie();
            return this;
        }

        public MovieBuilder withTitle(String title) {
            getBuildedMovie().setTitle(title);
            return this;
        }

        public MovieBuilder withOriginalTitle(String originalTitle) {
            getBuildedMovie().setOriginalTitle(originalTitle);
            return this;
        }

        public MovieBuilder withReleaseDate(String releaseDate) {
            getBuildedMovie().setReleaseDate(releaseDate);
            return this;
        }

        public MovieBuilder withRuntime(String runtime) {
            getBuildedMovie().setRuntime(runtime);
            return this;
        }

        public MovieBuilder withLanguage(String language) {
            getBuildedMovie().setLanguage(language);
            return this;
        }

        public MovieBuilder withCountry(String country) {
            getBuildedMovie().setCountryOfOrigin(country);
            return this;
        }

        public MovieBuilder withDirectors(Set<String> directors) {
            getBuildedMovie().setDirectors(directors);
            return this;
        }

        public MovieBuilder withWriters(Set<String> writers) {
            getBuildedMovie().setWriters(writers);
            return this;
        }

        public MovieBuilder withGenres(Set<String> genres) {
            getBuildedMovie().setGenres(genres);
            return this;
        }

        public MovieBuilder withKeywords(Set<String> keywords) {
            getBuildedMovie().setKeywords(keywords);
            return this;
        }

        public MovieBuilder withStars(Set<String> stars) {
            getBuildedMovie().setStars(stars);
            return this;
        }

        public MovieBuilder withCovers(Set<String> covers) {
            getBuildedMovie().setCovers(covers);
            return this;
        }

        public Movie build() {
            var result = getBuildedMovie();
            reset();
            return result;
        }

    }
}
