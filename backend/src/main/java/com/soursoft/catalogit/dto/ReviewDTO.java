package com.soursoft.catalogit.dto;

import com.soursoft.catalogit.entity.Review;

public record ReviewDTO(Long reviewId, Integer rating, String reviewBody) {

    public static ReviewDTO from(Review review) {
        return new ReviewDTO(review.getReviewId(), review.getRating(), review.getReviewBody());
    }

}
