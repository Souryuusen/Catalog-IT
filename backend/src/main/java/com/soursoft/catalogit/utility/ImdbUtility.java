package com.soursoft.catalogit.utility;

import com.soursoft.catalogit.dto.ScrapedDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.ImdbParsingException;
import com.soursoft.catalogit.exception.MovieIdentifierValidationException;
import com.soursoft.catalogit.service.ScrappingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ImdbUtility {

    Logger logger = LoggerFactory.getLogger(ImdbUtility.class);

    private String movieUrl;

    private Document pageBody;

    public ImdbUtility() {}

    public ScrapedDataDTO scrapeImdb(String movieIdentifier) {
        if(!validateMovieIdentifier(movieIdentifier)) {
            throw new MovieIdentifierValidationException("Provided identifier \"" + movieIdentifier + "\" is not valid!");
        } else {
            ScrapedDataDTO data = new ScrapedDataDTO();
            data.setMovieIdentifier(movieIdentifier);
            try {
                Document moviePage = retrievePageBody("https://www.imdb.com/title/" + movieIdentifier);
                var movieTitle = parseMovieTitle(moviePage);
                var originalTitle = parseOriginalTitle(moviePage);
                var directors = parseDirectors(moviePage);
                var writers = parseWriters(moviePage);
                var genres = parseGenres(moviePage);
                var starActors = parseStarActors(moviePage);
                var keywords = parseKeywords(moviePage);
                var covers = parseCoverUrl(moviePage);
                var productionDetails = parseTitleDetails(moviePage);

                data.setTitle(movieTitle);
                data.setOriginalTitle(originalTitle);
                data.setDirectorsSet(directors);
                data.setWritersSet(writers);
                data.setGenresSet(genres);
                data.setStarActorsSet(starActors);
                data.setKeywordsSet(keywords);
                data.setCoverUrlsSet(covers);
                data.setProductionDetailsMap(productionDetails);
                data.setRuntime(data.getProductionDetailsMap().get("RUNTIME"));
                data.setLanguage(data.getProductionDetailsMap().get("LANGUAGE"));
                data.setCountry(data.getProductionDetailsMap().get("COUNTRY"));
                data.setReleaseDate(data.getProductionDetailsMap().get("RELEASE_DATE"));

                return data;
            } catch (IOException e) {
                throw new ImdbParsingException("Exception Occurred during parsing of IMDB page!", e);
            }
        }
    }

    private boolean validateMovieIdentifier(String movieIdentifier) {
        return movieIdentifier.matches("(?i)^tt[0-9]{1,62}$");
    }

    private Document retrievePageBody(String url) throws IOException {
        Document page = Jsoup.connect(url)
                .header("Accept-Language", "en")
                .get();
        return page;
    }

    private String parseMovieTitle(Document doc) {
        String movieTitle = doc.select(".hero__primary-text").text();
        logger.debug("Parsed Movie Title: " + StringUtils.formatToCamelCase(movieTitle));
        return StringUtils.formatToCamelCase(movieTitle);
    }

    private String parseOriginalTitle(Document doc) {
        String originalTitle = doc.select("h1:has(.hero__primary-text)+div").text();
        if(originalTitle.matches("^Original title: .+")) {
            originalTitle = originalTitle.replace("Original title:", "").trim();
            originalTitle = StringUtils.formatToCamelCase(originalTitle);
        } else {
            originalTitle = "";
        }
        logger.debug("Parsed Original Movie Title: " + originalTitle);
        return originalTitle;
    }

    private Set<String> parseDirectors(Document doc) {
        var result = new HashSet<String>();

        Elements foundElements = doc.select("li div a[href*=\"tt_ov_dr\"]");
        if(!foundElements.isEmpty()) {
            foundElements.stream()
                    .map(e -> e.text())
                    .forEach(director -> {
                        result.add(StringUtils.formatToCamelCase(director));
                        logger.debug("Parsed Director: " + director);
                    });
        } else {
            throw new ImdbParsingException("Parsing Exception: No directors were found during parsing operation!");
        }
        return result;
    }

    private Set<String> parseWriters(Document doc) {
        var result = new HashSet<String>();

        Elements foundElements = doc.select("li div a[href*=\"tt_ov_wr\"]");
        if(!foundElements.isEmpty()) {
            foundElements.stream()
                    .distinct()
                    .map(e -> e.text().trim())
                    .filter(e -> !e.equalsIgnoreCase(""))
                    .forEach(writer -> {
                        result.add(StringUtils.formatToCamelCase(writer));
                        logger.debug("Parsed Writer: " + writer);
                    });
        } else {
            throw new ImdbParsingException("Parsing Exception: No writers were found during parsing operation!");
        }
        return result;
    }

    private Set<String> parseGenres(Document doc) {
        var result = new HashSet<String>();

        Elements foundElements = doc.select("a[href*=\"tt_ov_inf\"]");
        if(!foundElements.isEmpty()) {
            foundElements.stream()
                    .map(e -> e.text())
                    .forEach(genre -> {
                        result.add(StringUtils.formatToCamelCase(genre));
                        logger.debug("Parsed Genre: " + genre);
                    });
        } else {
            throw new ImdbParsingException("Parsing Exception: No genres were found during parsing operation!");
        }
        return result;
    }

    private Set<String> parseStarActors(Document doc) {
        var result = new HashSet<String>();

        Elements foundElements = doc.select("li div a[href*=\"tt_ov_st\"]");
        if(!foundElements.isEmpty()) {
            foundElements.stream()
                    .map(e -> e.text().trim())
                    .filter(e -> !e.equalsIgnoreCase(""))
                    .distinct()
                    .forEach(starActor -> {
                        result.add(StringUtils.formatToCamelCase(starActor));
                        logger.debug("Parsed star actor: " + starActor);
                    });
        }
        return result;
    }

    private Set<String> parseKeywords(Document doc) {
        var result = new TreeSet<String>();
        var baseUrl = doc.baseUri();
        try {
            Document keywordsPage = retrievePageBody(baseUrl + "keywords/");
            Elements keywords = keywordsPage.select("a[href*=\"ttkw_kw\"]");
            keywords.stream()
                    .distinct()
                    .map(keywordElement -> keywordElement.text().trim())
                    .filter(keyword -> !keyword.equalsIgnoreCase(""))
                    .forEach(keyword -> {
                        logger.debug("Parsed keyword: " + keyword);
                        if(result.size() < 50) {
                            result.add(StringUtils.formatToCamelCase(keyword));
                        }
                    });
        } catch (IOException e) {
            throw new ImdbParsingException("Parsing exception has occurred during processing keywords!");
        }
        return result;
    }

    private Set<String> parseCoverUrl(Document doc) {
        var result = new HashSet<String>();
        String coversPageUrl = "https://www.imdb.com" + doc.select("a[href*=\"tt_ov_i\"]").attr("href");

        try {
            Document coversPage = retrievePageBody(coversPageUrl);
            Elements coverElements = coversPage.select("div[style*=\"max-height\"] img");
            coverElements.stream()
                    .distinct()
                    .map(element -> element.attr("src"))
                    .forEach(coverUrl -> {
                        logger.debug("Parsed cover url: " + coverUrl);
                        result.add(coverUrl);
                    });
        } catch (IOException e) {
            throw new ImdbParsingException("Parsing exception has occurred during processing cover urls!");
        }

        return result;
    }

    private Map<String, String> parseTitleDetails(Document doc) {
        Map<String, String> detailsMap = new HashMap<>();
        // Runtime Details Parsing
        Elements titleTechSpecsSection = doc.select("div[data-testid=\"title-techspecs-section\"]");
        Element durationElement = titleTechSpecsSection
                .select("li[data-testid=\"title-techspec_runtime\"] span + div").first();
        detailsMap.put("RUNTIME", durationElement.text());

        // Production Details Parsing
        Elements titleDetailsSection = doc.select("div[data-testid=\"title-details-section\"]");
        titleDetailsSection = titleDetailsSection.select("ul li div");

        // Release Date
        Element releaseDateElement = titleDetailsSection.select("a[href*=\"tt_dt_rdat\"]").first();
        detailsMap.put("RELEASE_DATE", releaseDateElement.text());

        // Country Of Origin
        Elements countryElement = titleDetailsSection.select("a[href*=\"tt_dt_cn\"]");
        var countries = countryElement.stream()
                .distinct()
                .map(el -> StringUtils.formatToCamelCase(el.text()))
                .peek(e -> logger.debug("Parsed Country Of Origin: " + e))
                .collect(Collectors.joining(", "));
        detailsMap.put("COUNTRY", countries);

        // Language
        Elements languageElement = titleDetailsSection.select("a[href*=\"tt_dt_ln\"]");
        var languages = languageElement.stream()
                .distinct()
                .map(el -> StringUtils.formatToCamelCase(el.text()))
                .peek(e -> logger.debug("Parsed Language: " + e))
                .collect(Collectors.joining(", "));
        detailsMap.put("LANGUAGE", languages);

        return detailsMap;
    }
}
