package com.samaniasoft.toproleplay.repository;

import java.util.Collection;
import java.util.List;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.domain.Streamer;
import com.samaniasoft.toproleplay.domain.Usuario;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StreamerRepository extends JpaRepository<Streamer, Long>{
    //Para pesquisar pelo nome
    List<Streamer> findByNome(String nome, Pageable pageable);


    //Metodos para deletar o streamer, ele é pai então tem que deletar os filhos primeiro
    @Modifying
    @Query(value = "DELETE FROM grupo_membros WHERE grupo_membros.id_streamer= :id",
    nativeQuery = true)
    void deleteGrupoMembros(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM grupo_lideres WHERE grupo_lideres.id_streamer = :id",
    nativeQuery = true)
    void deleteGrupoLideres(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM clipe WHERE clipe.streamer_id = :id",
    nativeQuery = true)
    void deleteClipe(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM cidade_streamers WHERE cidade_streamers.id_streamer = :id",
    nativeQuery = true)
    void deleteCidadeStreamer(@Param("id") Long id);
    //------



    
    
}
