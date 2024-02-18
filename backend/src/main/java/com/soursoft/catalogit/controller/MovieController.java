package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.MovieAlreadyExistsException;
import com.soursoft.catalogit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/movies")
    public List<Movie> fetchAllMovies() {
        return this.service.findAllMovies();
    }

    @GetMapping("/movies/{movieId}")
    public Movie retrieveMovieById(@PathVariable long movieId) {
        return this.service.findMovieById(movieId);
    }

    @PostMapping(value = "/movies/create", consumes = "application/json", produces = "application/json")
    public Movie createNewMovie(@RequestBody Movie movie) {
        if(this.service.verifyMovieExistsByIdentifier(movie.getMovieIdentifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            return this.service.save(movie);
        }
    }
}
