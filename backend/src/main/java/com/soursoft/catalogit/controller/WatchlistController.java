package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.service.ReviewService;
import com.soursoft.catalogit.service.UserService;
import com.soursoft.catalogit.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/watchlist")
public class WatchlistController {

    private UserService userService;
    private WatchlistService watchlistService;
    private ReviewService reviewService;

    @Autowired
    public WatchlistController(UserService userService, WatchlistService watchlistService,
                               ReviewService reviewService) {
        this.userService = userService;
        this.watchlistService = watchlistService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Set<WatchlistElementDTO>> getWatchlistByUserId(@PathVariable Long userId) {
        User user = this.userService.findUserById(userId);
        Set<WatchlistElementDTO> result = this.watchlistService.getUserWatchlistDTO(user);

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

}
