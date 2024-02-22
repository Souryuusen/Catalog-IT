package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre implements Comparable<Genre>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private long genreId;

    @NotNull
    @Length(min = 1, max = 32)
    @Column(name = "genre_name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonBackReference
    private final Set<Movie> genreMovies = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        this();
        this.name = name;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMovie(Movie movie) {
        this.genreMovies.add(movie);
    }

    public void removeMovie(Movie movie) {
        this.genreMovies.remove(movie);
    }

    @Override
    public int compareTo(Genre o) {
        int compareResult;

        if (o.getClass() != this.getClass()) throw new ClassCastException("Cannot compare objects of different classes");

        if(getGenreId() != 0 && o.getGenreId() != 0) {
            compareResult = Long.compare(getGenreId(), o.getGenreId());
        } else {
            compareResult = getName().compareTo(o.getName());
        }

        return compareResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getName(), genre.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", name='" + name + '\'' +
                '}';
    }
}
