package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "writers")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writer_id")
    private long writerId;

    @NotNull
    @Length(min = 1, max = 60)
    @Column(name = "writer_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "writers")
    @JsonBackReference
    private Set<Movie> writtenMovies = new HashSet<>();

    public Writer() {
    }

    public Writer(String name) {
        this.name = name;
    }

    public long getWriterId() {
        return writerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getWrittenMovies() {
        return writtenMovies;
    }

    public void setWrittenMovies(Set<Movie> writtenMovies) {
        this.writtenMovies = writtenMovies;
    }

    public void addMovie(Movie m) {
        this.writtenMovies.add(m);
    }

    public void removeMovie(Movie m) {
        this.writtenMovies.remove(m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return Objects.equals(getName(), writer.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Writer{" +
                "writerId=" + writerId +
                ", name='" + name + '\'' +
                '}';
    }
}
