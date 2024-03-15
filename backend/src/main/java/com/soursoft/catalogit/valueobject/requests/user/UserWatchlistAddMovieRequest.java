package com.soursoft.catalogit.valueobject.requests.user;

public class UserWatchlistAddMovieRequest {

    private Long userId;

    private Long movieId;

    public UserWatchlistAddMovieRequest(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
