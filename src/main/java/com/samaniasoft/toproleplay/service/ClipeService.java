package com.samaniasoft.toproleplay.service;

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

    // ---------------------Post------------------------------------
    public ClipeDTO insert(Clipe clipe) {
        Assert.isNull(clipe.getId(), "Não foi possivel inserir o seu Post");
        return ClipeDTO.create(clipeRepository.save(clipe));
    }

    // ---------------------Delete------------------------------------
    public void delete(Long id){
        clipeRepository.deleteById(id);
    }

}
