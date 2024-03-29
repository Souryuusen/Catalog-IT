package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soursoft.catalogit.dto.ReviewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "reviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"rev_rating", "rev_body"})
})
public class Review implements Comparable<Review> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rev_id")
    private Long reviewId;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "rev_owner", referencedColumnName = "wid")
    private WatchlistElement owner;

    @Column(name = "rev_rating", nullable = false)
    @Range(min = 0, max = 100, message = "Review rating should have value from range 0 - 100")
    private Integer rating;

    @Column(name = "rev_body", nullable = false)
    @Size(min = 1, max = 500, message = "Review body should have length from range 10 - 500 characters")
    private String reviewBody;

    public Review() {

    }

    public Review(Integer rating, String reviewBody) {
        this.rating = rating;
        this.reviewBody = reviewBody;
    }

    public Review(Long reviewId, WatchlistElement owner, Integer rating, String reviewBody) {
        this.reviewId = reviewId;
        this.owner = owner;
        this.rating = rating;
        this.reviewBody = reviewBody;
    }

    public ReviewDTO convertToDTO() {
        return new ReviewDTO(this.reviewId, this.rating, this.reviewBody);
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public WatchlistElement getOwner() {
        return owner;
    }

    public void setOwner(WatchlistElement owner) {
        this.owner = owner;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(Review o) {
        return Integer.compare(this.rating, o.rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(getReviewId(), review.getReviewId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewId());
    }
}
