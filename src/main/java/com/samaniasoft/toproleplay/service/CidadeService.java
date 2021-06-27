package com.samaniasoft.toproleplay.service;

import com.samaniasoft.toproleplay.domain.Cidade;
import com.samaniasoft.toproleplay.dto.CidadeDTO;
import com.samaniasoft.toproleplay.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CidadeService {
    
    @Autowired
    CidadeRepository cidadeRepository;

    // ---------------------GET--------------------------------------
    public Page<Cidade> getCidades(Pageable pageable){
        return cidadeRepository.findAll(pageable);
    }

    // ---------------------Post------------------------------------
    public CidadeDTO insert(Cidade cidade) {
        Assert.isNull(cidade.getId(), "Não foi possivel inserir o seu Post");
        return CidadeDTO.create(cidadeRepository.save(cidade));
    }

}
