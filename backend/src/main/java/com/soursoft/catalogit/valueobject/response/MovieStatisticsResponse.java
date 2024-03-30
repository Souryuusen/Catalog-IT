package com.soursoft.catalogit.valueobject.response;

public record MovieStatisticsResponse(
        Long movieId,
        Integer averageRating,
        Long usersToWatchCount,
        Long usersFinishedCount,
        Long reviewsCount) {
}
