package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Noticia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long>{
    
}
