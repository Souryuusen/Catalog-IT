package com.soursoft.catalogit.exception;

import com.soursoft.catalogit.entity.Movie;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Movie movie) {
        super("Cannot find movie with id: " + movie.getMovieId());
    }

}
