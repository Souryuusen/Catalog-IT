package com.soursoft.catalogit.dto;

import com.soursoft.catalogit.entity.WatchlistElement;

import java.util.Set;
import java.util.stream.Collectors;

public record WatchlistElementDTO(Long watchlistElementId, Long ownerId, Long reviewedEntityId,
                                  Integer rating, boolean finished, Set<ReviewDTO> reviews) {
    public static WatchlistElementDTO from(WatchlistElement element) {
        var reviwes = element.getReviews()
                .stream()
                .map(r -> new ReviewDTO(r.getReviewId(), r.getRating(), r.getReviewBody())).collect(Collectors.toSet());
        return new WatchlistElementDTO(element.getWatchlistId(), element.getOwner().getUserId(),
                element.getReviewedEntity().getMovieId(), element.getRating(), element.isFinished(), reviwes);
    }

}
