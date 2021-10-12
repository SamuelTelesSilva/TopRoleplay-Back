package com.samaniasoft.toproleplay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samaniasoft.toproleplay.domain.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

    //Para pesquisar pelo titulo
    @Query(value = "SELECT * FROM Evento WHERE titulo LIKE %:titulo% ", nativeQuery = true)
    Page<Evento> searchByTituloLike(@Param("titulo") String titulo, Pageable pageable);
}
