package com.soursoft.catalogit.service;

import com.soursoft.catalogit.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    private WatchlistRepository repository;

    @Autowired
    public WatchlistService(WatchlistRepository repository) {
        this.repository = repository;
    }
}
