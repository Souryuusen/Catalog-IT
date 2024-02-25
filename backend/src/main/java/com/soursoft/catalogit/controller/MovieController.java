package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.MovieShortDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.MovieAlreadyExistsException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.utility.ImdbUtility;
import com.soursoft.catalogit.valueobject.requests.MovieScrapePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/movies/short")
    public List<MovieShortDataDTO> fetchAllMoviesShorData() {
        return this.service.findAllMoviesShortData();
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

    @GetMapping(value = "movies/search/{movieTitle}")
    public List<String> getIdentifiersFromTitle(@PathVariable String movieTitle) {
        return this.service.obtainIdentifiersFromTitle(movieTitle);
    }

    @PostMapping(value = "/movies/scrape", consumes = "application/json", produces = "application/json")
    public Movie createMovieByScrapping(@RequestBody MovieScrapePostRequest request) {
        if(this.service.verifyMovieExistsByIdentifier(request.getIdentifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            MovieDataDTO movieDataDTO = imdbUtility.scrapeImdb(request.getIdentifier());
            Movie newMovie = this.service.createFromData(movieDataDTO);
            return this.service.save(newMovie);
        }
    }
}
