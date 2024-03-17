package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.WatchlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistEntity, Long> {



}
