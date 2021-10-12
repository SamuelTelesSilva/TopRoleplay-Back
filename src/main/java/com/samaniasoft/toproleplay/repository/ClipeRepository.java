package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Clipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public interface ClipeRepository extends JpaRepository<Clipe, Long>{

    //Para pesquisar pelo nome
    @Query(value = "SELECT * FROM Clipe WHERE titulo LIKE %:titulo% ", nativeQuery = true)
    Page<Clipe> searchByTituloLike(@Param("titulo") String titulo, Pageable pageable);
}
