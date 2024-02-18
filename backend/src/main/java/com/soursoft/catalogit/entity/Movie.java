package com.soursoft.catalogit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

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

    @NaturalId
    @NotNull(message = "Provided movie identifier cannot be null.")
    @Length(min = 1, max = 64, message = "Provided movie identifier should have length between {min} and {max}.")
    @Pattern(regexp = "(?i)^tt[0-9]{1,62}$", message = "Provided movie identifier should match {regexp}")
    @Column(name = "movie_identifier", nullable = false, unique = true)
    private String movieIdentifier;

    public Movie() {
        this.movieIdentifier = "";
        this.title = "";
    }

    public Movie(String movieIdentifier, String title) {
        this.movieIdentifier = movieIdentifier;
        this.title = title;
    }

    public Movie(Movie movie) {
        this.title = movie.getTitle();
        this.movieIdentifier = movie.getMovieIdentifier();
    }

    public long getMovieId() {
        return movieId;
    }

    public String getMovieIdentifier() {
        return movieIdentifier;
    }

    public void setMovieIdentifier(String movieIdentifier) {
        this.movieIdentifier = movieIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
