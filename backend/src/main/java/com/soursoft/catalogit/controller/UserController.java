package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.MovieShortDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.UserByIdNotFoundException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.service.UserService;
import com.soursoft.catalogit.valueobject.requests.user.UserWatchlistAddMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/user")
public class UserController {

    private UserService userService;
    private MovieService movieService;

    @Autowired
    public UserController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<Set<MovieShortDataDTO>> getUserWatchlist(@PathVariable Long userId) {
        Set<Movie> userWatchlist = this.userService.fetchUserWatchlist(userId);
        var result = userWatchlist.stream().map(movie -> new MovieShortDataDTO(movie)).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<Set<MovieDataDTO>> addMovieToUserWatchlist(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistAddMovieRequest request) {
        var user = this.userService.findUserById(userId);
        if(user != null && user.getUserId() == userId) {
            var movie = movieService.findMovieById(request.getMovieId());
            this.userService.addMovieToWatchlist(user, movie);
            var userWatchlist = this.userService.fetchUserWatchlist(user.getUserId());
            var mappedWatchlist = userWatchlist.stream().map((m) -> new MovieShortDataDTO(m)).collect(Collectors.toSet());
            return new ResponseEntity(mappedWatchlist, HttpStatus.OK);
        } else {
            throw new UserByIdNotFoundException("User with id " + request.getUserId() + " has not been found!");
        }
    }

}
