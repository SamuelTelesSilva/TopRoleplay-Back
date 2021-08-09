package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.dto.GrupoDTO;
import com.samaniasoft.toproleplay.repository.GrupoRepository;

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

}
