package com.samaniasoft.toproleplay.service;

import java.util.List;
import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.dto.GrupoDTO;
import com.samaniasoft.toproleplay.repository.GrupoRepository;
import com.samaniasoft.toproleplay.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class GrupoService {
    
    @Autowired
    GrupoRepository grupoRepository;

    // ---------------------GET--------------------------------------
    public Page<Grupo> getGrupos(Pageable pageable){
        return grupoRepository.findAll(pageable);
    }

    public Page<Grupo> getTopGrupos(Pageable pageable){
        return grupoRepository.findAll(pageable);
    }

    public Page<Grupo> getGroupByNameLike(String nome, Pageable pageable){
        return grupoRepository.searchByNameLike(nome, pageable);
    }

    public List<Grupo> getGroupById(Long id_grupo){
        return grupoRepository.getGroupByIdModified(id_grupo);
    }

    /*
    public GrupoDTO getGroupById(Long id){
        Optional<Grupo> grupo = grupoRepository.findById(id);
        return grupo.map(GrupoDTO::create).orElseThrow(() -> new ObjectNotFoundException("Grupo não encontrado"));
    }
    */

    // ---------------------Post------------------------------------
    public GrupoDTO insert(Grupo grupo) {
        Assert.isNull(grupo.getId(), "Não foi possivel inserir o seu Post");
        return GrupoDTO.create(grupoRepository.save(grupo));
    }

    //Associar o Lider do grupo
    @Transactional
    public void insertLeaderGroup(Long id_grupo, Long id_streamer){
        grupoRepository.saveLeadersGroup(id_grupo, id_streamer);
    }

    //Associar os membros do grupo
    @Transactional
    public void insertMemberGroup(Long id_grupo, Long id_streamer){
        grupoRepository.saveMembersGroup(id_grupo, id_streamer);
    }

    //Associar a cidade aonde o grupo joga
    @Transactional
    public void insertCityGroup(Long id_cidade, Long id_grupo){
        grupoRepository.saveCityGroup(id_cidade, id_grupo);
    }


    // ---------------------Update------------------------------------
    public GrupoDTO update(Grupo grupo, Long id){
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Grupo> optional = grupoRepository.findById(id);

        if(optional.isPresent()){
            Grupo db = optional.get();
            db.setNome(grupo.getNome());
            db.setGenero(grupo.getGenero());
            db.setCoracao(grupo.getCoracao());
            db.setUrlImageCard(grupo.getUrlImageCard());
            db.setUrlImageCapa(grupo.getUrlImageCapa());

            grupoRepository.save(db);
            return GrupoDTO.create(db);
        }else{
            return null;
        }

    }

    public GrupoDTO votacao(Long id){
        Assert.notNull(id, "Não foi possível efetuar a votação");

        Optional<Grupo> optional = grupoRepository.findById(id);

        if(optional.isPresent()){
            Grupo db = optional.get();
            db.setCoracao( db.getCoracao() + 5);
            
            grupoRepository.save(db);
            return GrupoDTO.create(db);
        }else{
            return null;
        }

    }


    // ---------------------Delete------------------------------------
    @Transactional
    public void deleteAllByGroupId(Long id){
        grupoRepository.deleteAllGroupLeader(id);
        grupoRepository.deleteAllGroupMember(id);
        grupoRepository.deleteAllGroupCity(id);
        grupoRepository.deleteById(id);
    }

    @Transactional
    public void deleteLeaderGroup(Long id_grupo, Long id_streamer){
        grupoRepository.deleteLeaderGroup(id_grupo, id_streamer);
    }

    @Transactional
    public void deleteMemberGroup(Long id_grupo, Long id_streamer){
        grupoRepository.deleteMemberGroup(id_grupo, id_streamer);
    }

    @Transactional
    public void deleteCityGroup(Long id_cidade, Long id_grupo){
        grupoRepository.deleteCityGroup(id_cidade, id_grupo);
    }

}
