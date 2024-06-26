package com.samaniasoft.toproleplay.repository;

import java.util.List;

import com.samaniasoft.toproleplay.domain.Grupo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
    

    //Para pesquisar pelo nome
    @Query(value = "SELECT * FROM Grupo WHERE nome LIKE %:nome% ", nativeQuery = true)
    Page<Grupo> searchByNameLike(@Param("nome") String nome, Pageable pageable);


    //Pegar o grupo por id, metodo semelhante ao nativo do jpa
    @Query(value = "SELECT * FROM Grupo gm WHERE gm.id = :id", nativeQuery = true)
    List<Grupo> getGroupByIdModified(@Param("id") Long id);



    //Pegar os membros do grupo



    //-------------------------------------------------------------------
    /**
     *  Métodos para realizar as associações entre Grupo e Líderes
     * @param id_grupo
     * @param id_streamer
    */
    @Modifying
    @Query( value = "INSERT INTO grupo_lideres (id_grupo, id_streamer) VALUES ( :id_grupo, :id_streamer)", 
        nativeQuery= true)
    void saveLeadersGroup(@Param("id_grupo") Long id_grupo, @Param("id_streamer") Long id_streamer);  

    /**
     * Métodos para realizar as associações entre Grupo e Membros
     * @param id_grupo
     * @param id_streamer
     */
    @Modifying
    @Query( value = "INSERT INTO grupo_membros (id_grupo, id_streamer) VALUES ( :id_grupo, :id_streamer)", 
        nativeQuery= true)
    void saveMembersGroup(@Param("id_grupo") Long id_grupo, @Param("id_streamer") Long id_streamer);

    /**
     * Métodos para realizar as associações entre Grupo e Cidades
     * @param id_cidade
     * @param id_grupo
     */
    @Modifying
    @Query( value = "INSERT INTO cidade_grupos (id_cidade, id_grupo) VALUES ( :id_cidade, :id_grupo)", 
        nativeQuery= true)
    void saveCityGroup(@Param("id_cidade") Long id_cidade, @Param("id_grupo") Long id_grupo);

    //-------------------------------------------------------------------------------------------
    //Métodos para Deletar o grupo por completo
    @Modifying
    @Query(value = "DELETE FROM grupo_lideres WHERE grupo_lideres.id_grupo = :id",
        nativeQuery = true)
    void deleteAllGroupLeader(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM grupo_membros WHERE grupo_membros.id_grupo = :id",
        nativeQuery = true)
    void deleteAllGroupMember(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM cidade_grupos WHERE cidade_grupos.id_grupo = :id",
        nativeQuery = true)
    void deleteAllGroupCity(@Param("id") Long id);


    //---------------------------------------------------------------------------------
    //Métodos para remover apenas Lideres/membros/cidades do grupo

    /**
     * Método para remover a associação do lider do grupo
     * @param id
     */
    @Modifying
    @Query(value = "DELETE FROM grupo_lideres WHERE grupo_lideres.id_grupo = :id_grupo AND grupo_lideres.id_streamer = :id_streamer",
        nativeQuery = true)
    void deleteLeaderGroup(@Param("id_grupo") Long id_grupo, @Param("id_streamer") Long id_streamer);

    /**
     * Método para remover a associação do membro do grupo
     * @param id
    */
    @Modifying
    @Query(value = "DELETE FROM grupo_membros WHERE grupo_membros.id_grupo = :id_grupo AND grupo_membros.id_streamer = :id_streamer",
        nativeQuery = true)
    void deleteMemberGroup(@Param("id_grupo") Long id_grupo, @Param("id_streamer") Long id_streamer);

    /**
     * Método para remover a associação da cidade do grupo
     * @param id
    */
    @Modifying
    @Query(value = "DELETE FROM cidade_grupos WHERE cidade_grupos.id_cidade = :id_cidade AND cidade_grupos.id_grupo = :id_grupo",
        nativeQuery = true)
    void deleteCityGroup(@Param("id_cidade") Long id_cidade, @Param("id_grupo") Long id_grupo);



}

