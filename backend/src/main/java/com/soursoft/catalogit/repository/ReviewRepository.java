package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository {

    Optional<Review> findByReviewId(Long reviewId);

}
