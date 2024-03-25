package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Review;
import com.soursoft.catalogit.exception.ReviewNotFoundException;
import com.soursoft.catalogit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review findReviewById(Long reviewId) {
        Optional<Review> optionalReview = this.repository.findByReviewId(reviewId);
        if(optionalReview.isPresent()) {
            return optionalReview.get();
        } else {
            throw new ReviewNotFoundException(String.format("Review by ID: %d has not been found", reviewId));
        }
    }

}
