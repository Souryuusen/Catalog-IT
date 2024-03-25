package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.MovieShortDataDTO;
import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.UserByIdNotFoundException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.service.UserService;
import com.soursoft.catalogit.service.WatchlistService;
import com.soursoft.catalogit.valueobject.requests.user.UserWatchlistMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/user")
public class UserController {

    private UserService userService;
    private MovieService movieService;
    private WatchlistService watchlistService;

    @Autowired
    public UserController(UserService userService, MovieService movieService, WatchlistService watchlistService) {
        this.userService = userService;
        this.movieService = movieService;
        this.watchlistService = watchlistService;
    }

    @GetMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<Set<MovieShortDataDTO>> getUserWatchlist(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        Set<WatchlistElement> userWatchlist = this.watchlistService.getUserWatchlist(user);
        var result = userWatchlist.stream()
                    .map(element -> new MovieShortDataDTO(element.getReviewedEntity()))
                    .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<MovieDataDTO> addMovieToUserWatchlist(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistMovieRequest request) {
        var user = this.userService.findUserById(userId);
        var movie = movieService.findMovieById(request.movieId());
        this.watchlistService.addMovieToUserWatchlist(user, movie);
        return new ResponseEntity(movie.convertToShortDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/watchlista")
    public ResponseEntity<Set<WatchlistElementDTO>> addMovieToUserWatchlista(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistMovieRequest request) {
        var user = this.userService.findUserById(userId);
        var movie = movieService.findMovieById(request.movieId());
        var a = this.watchlistService.getUserWatchlist(user)
                .stream()
                .map(we -> WatchlistElementDTO.from(we))
                .collect(Collectors.toSet());
        return new ResponseEntity(a, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<MovieDataDTO> removeMovieFromUserWatchlist(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistMovieRequest request) {
        var user = this.userService.findUserById(userId);
        var movie = movieService.findMovieById(request.movieId());
//        this.watchlistService.removeMovieFromUserWatchlist(user, movie);
//        this.userService.remove(user, movie);
        return new ResponseEntity(movie.convertToShortDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/watchlist/{movieId}")
    public ResponseEntity<MovieShortDataDTO> getMovieFromUserWatchlistById(@PathVariable Long userId,
                                                                        @PathVariable Long movieId) {
        var user = this.userService.findUserById(userId);
        var movie = this.movieService.findMovieById(movieId);
//        //var watchlist = user.getWatchlistSet();
//        //Optional<Movie> foundOptional = watchlist.stream().filter(movie -> movie.getMovieId() == movieId).findFirst();
//        var foundOptional = this.watchlistService.getWatchlistElementByUserAndMovie(user, movie);
////        if(foundOptional.isPresent()) {
//            return  new ResponseEntity<>(foundOptional.get().convertToShortDTO(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }
        return null;
    }

    @DeleteMapping(value = "/users/{userId}/watchlist/{movieId}")
    public ResponseEntity<MovieDataDTO> removeMovieFromUserWatchlistById(@PathVariable Long userId,
                                                                     @PathVariable Long movieId) {
        var user = this.userService.findUserById(userId);
        var movie = movieService.findMovieById(movieId);
//        this.userService.removeMovieFromWatchList(user, movie);
        return new ResponseEntity(movie.convertToShortDTO(), HttpStatus.OK);
    }



}
