package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

    //Para pesquisar pelo nome
    @Query(value = "SELECT * FROM cidade WHERE nome LIKE %:nome% ", nativeQuery = true)
    Page<Cidade> searchByNameLike(@Param("nome") String nome, Pageable pageable);

}
