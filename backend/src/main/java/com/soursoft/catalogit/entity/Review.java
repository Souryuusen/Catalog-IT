package com.soursoft.catalogit.entity;

import com.soursoft.catalogit.dto.ReviewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review implements Comparable<Review> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rev_id")
    private Long reviewId;

    @Column(name = "rev_rating", nullable = false, length = 3)
    @Size(min = 0, max = 100, message = "Review rating should have value from range 0 - 100")
    private int rating;

    @Column(name = "rev_body", nullable = false)
    @Length(min = 10, max = 500, message = "Review body should have length from range 10 - 500 characters")
    private String reviewBody;

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
