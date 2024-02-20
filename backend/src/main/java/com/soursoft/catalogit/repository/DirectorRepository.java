package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findDirectorByNameIgnoreCase(String name);

}
