package com.soursoft.catalogit.exception;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;

public class WatchlistElementAlreadyExistsException extends RuntimeException{

    public WatchlistElementAlreadyExistsException(String message) {
        super(message);
    }

    public WatchlistElementAlreadyExistsException(User user, Movie movie) {
        this(
                String.format(
                        "Watchlist element with User ID: %d and Movie ID: %d already exists!",
                        user.getUserId(),
                        movie.getMovieId()
                )
        );
    }

}
