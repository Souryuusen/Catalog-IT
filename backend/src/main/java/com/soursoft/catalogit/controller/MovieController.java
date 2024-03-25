package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.MovieShortDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.MovieAlreadyExistsException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.utility.ImdbUtility;
import com.soursoft.catalogit.valueobject.requests.MovieScrapePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/movie")
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
        var movieList = this.service.findAllMovies();
        return movieList.stream().map(m -> m.convertToShortDTO()).collect(Collectors.toList());
    }

    @GetMapping("/movies/{movieId}")
    public Movie retrieveMovieById(@PathVariable long movieId) {
        return this.service.findMovieById(movieId);
    }

    @PostMapping(value = "/movies", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie movie) {
        if(this.service.verifyMovieExistsByIdentifier(movie.getMovieIdentifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            Movie createdMovie = this.service.save(movie);
            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "movies/search/{movieTitle}")
    public List<String> getIdentifiersFromTitle(@PathVariable String movieTitle) {
        return this.service.obtainIdentifiersFromTitle(movieTitle);
    }

    @PostMapping(value = "/movies/scrape", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> createMovieByScrapping(@RequestBody MovieScrapePostRequest request) {
        if(this.service.verifyMovieExistsByIdentifier(request.identifier())) {
            throw new MovieAlreadyExistsException("Movie with provided identifier already exists!");
        } else {
            MovieDataDTO movieDataDTO = imdbUtility.scrapeImdb(request.identifier());
            Movie newMovie = this.service.createFromData(movieDataDTO);
            newMovie = this.service.save(newMovie);
            return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
        }
    }
}
