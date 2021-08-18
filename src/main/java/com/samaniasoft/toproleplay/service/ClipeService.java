package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Clipe;
import com.samaniasoft.toproleplay.dto.ClipeDTO;
import com.samaniasoft.toproleplay.repository.ClipeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ClipeService {
    
    @Autowired
    ClipeRepository clipeRepository;


    // ---------------------GET--------------------------------------
    public Page<Clipe> getClipes(Pageable pageable){
        return clipeRepository.findAll(pageable);
    }

    public Page<Clipe> getClipeByTituloLike(String titulo, Pageable pageable){
        return clipeRepository.searchByTituloLike(titulo, pageable);
    }

    // ---------------------Post------------------------------------
    public ClipeDTO insert(Clipe clipe) {
        Assert.isNull(clipe.getId(), "Não foi possivel inserir o seu Post");
        return ClipeDTO.create(clipeRepository.save(clipe));
    }

    // ---------------------Update------------------------------------
    public ClipeDTO update(Clipe clipe, Long id){
        Assert.notNull(id, "Não foi possível atualizar o clipe");

        Optional<Clipe> optional = clipeRepository.findById(id);

        if(optional.isPresent()){
            Clipe db = optional.get();

            db.setTitulo(clipe.getTitulo());
            db.setUrl(clipe.getUrl());
            db.setCoracao(clipe.getCoracao());
            db.setUrlImageCard(clipe.getUrlImageCard());
            db.setUrlImageCapa(clipe.getUrlImageCapa());
            db.setStreamer(clipe.getStreamer());

            clipeRepository.save(db);
            return ClipeDTO.create(db);
        }else{
            return null;
        }
    }

    // ---------------------Delete------------------------------------
    public void delete(Long id){
        clipeRepository.deleteById(id);
    }

}
