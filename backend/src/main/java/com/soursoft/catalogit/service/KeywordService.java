package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Genre;
import com.soursoft.catalogit.entity.Keyword;
import com.soursoft.catalogit.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeywordService {

    private KeywordRepository repository;

    @Autowired
    public KeywordService(KeywordRepository repository) {
        this.repository = repository;
    }

    public Keyword save(Keyword keyword) {
        return this.repository.save(keyword);
    }

    public Optional<Keyword> findKeywordByNameIgnoreCase(String name) {
        return this.repository.findKeywordByNameIgnoreCase(name);
    }

    public Keyword ensureKeywordExist(String keywordName) {
        var fetchedKeyword = this.repository.findKeywordByNameIgnoreCase(keywordName);
        if(fetchedKeyword.isPresent()) {
            return fetchedKeyword.get();
        } else {
            return this.repository.save(new Keyword(keywordName));
        }
    }
}
