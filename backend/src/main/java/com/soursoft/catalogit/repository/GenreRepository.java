package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findGenreByNameIgnoreCase(String name);

}
