package com.soursoft.catalogit.service;

import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.WatchlistElementNotFoundException;
import com.soursoft.catalogit.exception.WatchlistNotFoundException;
import com.soursoft.catalogit.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    private WatchlistRepository repository;

    @Autowired
    public WatchlistService(WatchlistRepository repository) {
        this.repository = repository;
    }

    public Set<WatchlistElement> getUserWatchlist(User user) {
        var result = this.repository.findAllByOwner(user);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new WatchlistNotFoundException("No watchlist found for user id" + user.getUserId());
        }
    }

    public WatchlistElement createNewWatchlistElementForUser(User user, Movie movie){
        return this.save(WatchlistElement.from(user, movie));
    }

    public Set<WatchlistElementDTO> getUserWatchlistDTO(User user) {
        Set<WatchlistElement> userWatchlist = this.getUserWatchlist(user);

        Set<WatchlistElementDTO> result = userWatchlist.stream()
                .map(element -> WatchlistElementDTO.from(element))
                .collect(Collectors.toSet());
        return result;
    }

    public Optional<WatchlistElement> getWatchlistElementByUserAndMovie(User user, Movie movie) {
        return this.repository.findElementByUserAndMovie(user, movie);
    }

    public WatchlistElement save(WatchlistElement element) {
        return this.repository.save(element);
    }


}