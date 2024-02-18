package com.soursoft.catalogit.utility;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.ImdbParsingException;
import com.soursoft.catalogit.exception.MovieIdentifierValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImdbUtility {

    private String movieUrl;

    private Document pageBody;

    public ImdbUtility() {}

    public Movie scrapeImdb(String movieIdentifier) {
        if(!validateMovieIdentifier(movieIdentifier)) {
            throw new MovieIdentifierValidationException("Provided identifier \"" + movieIdentifier + "\" is not valid!");
        } else {
            try {
                Document moviePage = retrievePageBody("https://www.imdb.com/title/" + movieIdentifier);
                String movieTitle = parseMovieTitle(moviePage);
                String originalTitle = parseOriginalTitle(moviePage);
            } catch (IOException e) {
                throw new ImdbParsingException("Exception Occurred during parsing of IMDB page!", e);
            }
        }
        return null;
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
        System.out.println(StringUtils.formatToCamelCase(movieTitle));
        return movieTitle;
    }

    private String parseOriginalTitle(Document doc) {
        String originalTitle = doc.select("h1:has(.hero__primary-text)+div").text();
        if(originalTitle.matches("^Original title: .+")) {
            originalTitle = originalTitle.replace("Original title:", "").trim();
            originalTitle = StringUtils.formatToCamelCase(originalTitle);
        } else {
            originalTitle = "";
        }
        System.out.println(originalTitle);
        return originalTitle;
    }
}
