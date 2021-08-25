package com.samaniasoft.toproleplay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samaniasoft.toproleplay.domain.PlayerVideo;

public interface PlayerVideoRepository extends JpaRepository<PlayerVideo, Long>{

    //Para pesquisar pelo titulo
    @Query(value = "SELECT * FROM playerVideo WHERE titulo LIKE %:titulo% ", nativeQuery = true)
    Page<PlayerVideo> searchByTituloLike(@Param("titulo") String titulo, Pageable pageable);
}
