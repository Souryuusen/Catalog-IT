package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "keywords")
public class Keyword implements Comparable<Keyword> {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "keyword_id")
    private long keywordId;

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "keyword_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "keywords")
    @JsonBackReference
    private Set<Movie> moviesUsing = new TreeSet<>();

    public Keyword() {
    }

    public Keyword(String keywordName) {
        this.name = keywordName;
    }

    public long getKeywordId() {
        return keywordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String keywordName) {
        this.name = keywordName;
    }

    public Set<Movie> getMoviesUsing() {
        return moviesUsing;
    }

    public void setMoviesUsing(Set<Movie> moviesUsing) {
        this.moviesUsing = moviesUsing;
    }

    public void addMovie(Movie movie) {
        this.moviesUsing.add(movie);
    }

    public void removeMovie(Movie movie) {
        this.moviesUsing.remove(movie);
    }

    @Override
    public int compareTo(Keyword o) {
        int compareResult;

        if(o.getClass() != this.getClass()) throw new ClassCastException("Cannot compare objects of different classes");

        if(getKeywordId() != 0 && o.getKeywordId() != 0) {
            compareResult = Long.compare(getKeywordId(), o.getKeywordId());
        } else {
            compareResult = getName().compareTo(o.getName());
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(getName(), keyword.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "keywordId=" + keywordId +
                ", keywordName='" + name + '\'' +
                ", moviesUsing=" + moviesUsing +
                '}';
    }
}
