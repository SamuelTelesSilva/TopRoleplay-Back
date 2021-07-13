package com.samaniasoft.toproleplay.service;

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

}
