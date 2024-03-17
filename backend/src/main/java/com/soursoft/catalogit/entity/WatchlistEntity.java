package com.soursoft.catalogit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "user_watchlist")
public class WatchlistEntity {

    private User user;
    private Movie watchlistElement;
    private int rating;
    private boolean watched;
    private Set<Review> reviews;

}
