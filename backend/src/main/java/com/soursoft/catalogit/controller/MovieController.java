package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.ScrapedDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.MovieAlreadyExistsException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.utility.ImdbUtility;
import com.soursoft.catalogit.valueobject.requests.MovieScrapePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieService service;
    private ImdbUtility imdbUtility;

    @Autowired
    public MovieController(MovieService service, ImdbUtility utility) {
        this.service = service;
        this.imdbUtility = utility;
    }

    @GetMapping("/movies")
    public List<Movie> fetchAllMovies() {
        return this.service.findAllMovies();
    }

    @GetMapping("/movies/{movieId}")
    public Movie retrieveMovieById(@PathVariable long movieId) {
        return this.service.findMovieById(movieId);
    }

    @PostMapping(value = "/movies", consumes = "application/json", produces = "application/json")
    public Movie createNewMovie(@RequestBody Movie movie) {
        if(this.service.verifyMovieExistsByIdentifier(movie.getMovieIdentifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            return this.service.save(movie);
        }
    }

    @PostMapping(value = "/movies/scrape", consumes = "application/json", produces = "application/json")
    public Movie createMovieByScrapping(@RequestBody MovieScrapePostRequest request) {
        if(this.service.verifyMovieExistsByIdentifier(request.getIdentifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            ScrapedDataDTO scrapedDataDTO = imdbUtility.scrapeImdb(request.getIdentifier());
            Movie newMovie = new Movie(scrapedDataDTO);
            return this.service.save(newMovie);
        }
    }
}
