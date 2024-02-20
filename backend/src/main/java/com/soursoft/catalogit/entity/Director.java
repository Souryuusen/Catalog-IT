package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private long directorId;

    @Length(min = 1, max = 60)
    @NotNull
    @Column(name = "director_name", nullable = false, unique = true)
    private String name;

    @JsonBackReference
    @ManyToMany( mappedBy = "directors")
    private Set<Movie> directedMovies = new HashSet<>();

    public Director() {
    }

    public Director(String name) {
        this.name = name;
    }

    public long getDirectorId() {
        return directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(getName(), director.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", name='" + name + '\'' +
                '}';
    }
}
