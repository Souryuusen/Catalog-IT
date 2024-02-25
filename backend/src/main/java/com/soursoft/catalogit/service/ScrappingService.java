package com.soursoft.catalogit.service;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.utility.ImdbUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrappingService {

    private ImdbUtility imdbUtility;

    @Autowired
    public ScrappingService(ImdbUtility imdbUtility) {
        this.imdbUtility = imdbUtility;
    }

    public MovieDataDTO obtainMovieDataByIdentifier(String identifier) {
        return imdbUtility.scrapeImdb(identifier);
    }

    public List<String> searchForIdentifiersByTitle(String title) {
        return imdbUtility.searchForIdentifiersByTitle(title);
    }
}
