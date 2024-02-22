package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findKeywordByNameIgnoreCase(String name);

}
