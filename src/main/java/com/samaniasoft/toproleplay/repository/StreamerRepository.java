package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Streamer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StreamerRepository extends JpaRepository<Streamer, Long>{

    //Para pesquisar pelo nome
    @Query(value = "SELECT * FROM streamer WHERE nome LIKE %:nome% ", nativeQuery = true)
    Page<Streamer> searchByNameLike(@Param("nome") String nome, Pageable pageable);

    //Metodo para fazer o post na tabela cidade_streamer
    @Modifying
    @Query( value = "INSERT INTO cidade_streamers (id_cidade, id_streamer) VALUES ( :id_cidade, :id_streamer)", nativeQuery= true)
    void saveCidadeStreamers(@Param("id_cidade") Long id_cidade, @Param("id_streamer") Long id_streamer);  


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


    /**
     * Metodos para fazer uma modificação na associação do streamer
     * @param idCidadeAtual id da cidade atual
     * @param idStreamerAtual id do streamer atual
     * @param idCidadeNew id da cidade para fazer o update na cidade atual
     * @param idStreamerNew id do streamer para fazer o update no streamer atual
     */
    @Modifying
    @Query(value = "UPDATE cidade_streamers SET id_cidade = :idCidadeNew, id_streamer = :idStreamerNew WHERE cidade_streamers.id_cidade = :idCidadeAtual AND cidade_streamers.id_streamer = :idStreamerAtual",
        nativeQuery = true
    )
    void updateCidadeStreamers(
        @Param("idCidadeNew") Long idCidadeNew,
        @Param("idStreamerNew") Long idStreamerNew,
        @Param("idCidadeAtual") Long idCidadeAtual,
        @Param("idStreamerAtual") Long idStreamerAtual
    );





    
}
