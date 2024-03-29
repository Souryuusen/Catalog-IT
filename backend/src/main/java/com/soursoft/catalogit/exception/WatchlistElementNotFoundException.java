package com.soursoft.catalogit.exception;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WatchlistElementNotFoundException extends RuntimeException{

    public WatchlistElementNotFoundException(String message) {
        super(message);
    }

    public WatchlistElementNotFoundException(User user, Movie movie) {
        this(
                String.format(
                        "Watchlist element with User ID: %d and Movie ID: %d does not exists!",
                        user.getUserId(),
                        movie.getMovieId()
                )
        );
    }
}
