package com.samaniasoft.toproleplay.service;

import com.samaniasoft.toproleplay.domain.Noticia;
import com.samaniasoft.toproleplay.dto.NoticiaDTO;
import com.samaniasoft.toproleplay.repository.NoticiaRepository;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticiaService {
    
    @Autowired
    NoticiaRepository noticiaRepository;

    // ---------------------GET--------------------------------------
    public Page<Noticia> getNoticias(Pageable pageable){
        return noticiaRepository.findAll(pageable);
    }

    // ---------------------Post------------------------------------
    public NoticiaDTO insert(Noticia noticia) {
        Assert.isNull(noticia.getId(), "Não foi possivel inserir o seu Post");
        return NoticiaDTO.create(noticiaRepository.save(noticia));
    }
}
