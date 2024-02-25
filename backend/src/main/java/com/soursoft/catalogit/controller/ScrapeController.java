package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.service.ScrappingService;
import com.soursoft.catalogit.utility.ImdbUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScrapeController {

    private ScrappingService service;
    private ImdbUtility utility;

    @Autowired
    public ScrapeController(ScrappingService service, ImdbUtility utility) {
        this.service = service;
        this.utility = utility;
    }

    @GetMapping("/scrape/{movieIdentifier}")
    public MovieDataDTO scrapeByMovieIdentifier(@PathVariable String movieIdentifier) {
        var scrapedDTO = utility.scrapeImdb(movieIdentifier);

        return scrapedDTO;
    }
}
