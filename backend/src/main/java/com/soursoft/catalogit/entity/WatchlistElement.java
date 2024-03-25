package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_watchlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"w_owner_id", "w_movie_id"})
})
public class WatchlistElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wid")
    private Long watchlistId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "w_owner_id", referencedColumnName = "uid")
    private User owner;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "w_movie_id", referencedColumnName = "movie_id")
    private Movie reviewedEntity;

    @Column(name = "w_movie_rating")
    @Range(min = 0, max = 100, message = "Rating should have value from range 0 - 100.")
    private Integer rating;
    @Column(name = "w_finished")
    private boolean finished;
    @OneToMany(mappedBy = "reviewId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Review> reviews;

    public WatchlistElement() {
    }

    public WatchlistElement(User owner, Movie reviewedEntity) {
        this.owner = owner;
        this.reviewedEntity = reviewedEntity;
        this.reviews = new HashSet<>();
        this.finished = false;
        this.rating = 0;
    }

    public static WatchlistElement from(User owner, Movie reviewedEntity) {
        return new WatchlistElement(owner, reviewedEntity);
    }

    public Long getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(Long watchlistId) {
        this.watchlistId = watchlistId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Movie getReviewedEntity() {
        return reviewedEntity;
    }

    public void setReviewedEntity(Movie reviewedEntity) {
        this.reviewedEntity = reviewedEntity;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        if(this.reviews != null) {
            this.reviews.add(review);
        } else {
            this.reviews = new HashSet<>();
            this.reviews.add(review);
        }
    }

    public void removeReview(Review review) {
        if(this.reviews.contains(review)) {
            this.reviews.remove(review);
        } else {
            //todo: Add Exception for ReviewNotFound
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchlistElement that = (WatchlistElement) o;
        return Objects.equals(getOwner(), that.getOwner()) && Objects.equals(getReviewedEntity(), that.getReviewedEntity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwner(), getReviewedEntity());
    }
}
