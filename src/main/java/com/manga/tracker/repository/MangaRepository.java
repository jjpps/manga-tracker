package com.manga.tracker.repository;

import com.manga.tracker.model.MangaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaRepository extends JpaRepository<MangaModel, Long> {
    MangaModel findByUuid(String uuid);
}
