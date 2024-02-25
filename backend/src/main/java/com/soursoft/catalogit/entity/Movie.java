package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToMany( fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn( name = "movie_id"),
            inverseJoinColumns = @JoinColumn( name = "director_id")
    )
    private Set<Director> directors;

    @ManyToMany( fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
            name = "movie_writers",
            joinColumns = @JoinColumn( name = "movie_id"),
            inverseJoinColumns = @JoinColumn( name = "writer_id")
    )
    private Set<Writer> writers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn( name = "movie_id"),
            inverseJoinColumns = @JoinColumn( name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
            name = "movie_keywords",
            joinColumns = @JoinColumn( name = "movie_id"),
            inverseJoinColumns = @JoinColumn( name = "keyword_id")
    )
    private Set<Keyword> keywords;

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
                    String countryOfOrigin, String language, Set<Director> directors,
                        Set<Writer> writers, Set<Genre> genres, Set<Keyword> keywords,
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

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Writer> getWriters() {
        return writers;
    }

    public void setWriters(Set<Writer> writers) {
        this.writers = writers;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
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
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", runtime='" + runtime + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", language='" + language + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", movieIdentifier='" + movieIdentifier + '\'' +
                ", directors=" + directors +
                ", writers=" + writers +
                ", genres=" + genres +
                ", keywords=" + keywords +
                ", stars=" + stars +
                ", covers=" + covers +
                '}';
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

        public MovieBuilder withIdentifier(String identifier) {
            getBuildedMovie().setMovieIdentifier(identifier);
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

        public MovieBuilder withDirectors(Set<Director> directors) {
            getBuildedMovie().setDirectors(directors);
            return this;
        }

        public MovieBuilder withWriters(Set<Writer> writers) {
            getBuildedMovie().setWriters(writers);
            return this;
        }

        public MovieBuilder withGenres(Set<Genre> genres) {
            getBuildedMovie().setGenres(genres);
            return this;
        }

        public MovieBuilder withKeywords(Set<Keyword> keywords) {
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
