package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Cidade;
import com.samaniasoft.toproleplay.dto.CidadeDTO;
import com.samaniasoft.toproleplay.repository.CidadeRepository;
import com.samaniasoft.toproleplay.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class CidadeService {
    
    @Autowired
    CidadeRepository cidadeRepository;

    // ---------------------GET--------------------------------------
    public Page<Cidade> getCidades(Pageable pageable){
        return cidadeRepository.findAll(pageable);
    }

    public Page<Cidade> getTopCidades(Pageable pageable){
        return cidadeRepository.findAll(pageable);
    }

    public Page<Cidade> getCityByNameLike(String nome, Pageable pageable){
        return cidadeRepository.searchByNameLike(nome, pageable);
    }

    public CidadeDTO getCityById(Long id){
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cidade.map(CidadeDTO::create).orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrado"));
    }

    // ---------------------Post------------------------------------
    public CidadeDTO insert(Cidade cidade) {
        Assert.isNull(cidade.getId(), "Não foi possivel inserir o seu Post");
        return CidadeDTO.create(cidadeRepository.save(cidade));
    }

    // ---------------------Update--------------------------------------
    public CidadeDTO update(Cidade cidade, Long id){
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Cidade> optional = cidadeRepository.findById(id);

        if(optional.isPresent()){
            Cidade db = optional.get();

            db.setNome(cidade.getNome());
            db.setCoracao(cidade.getCoracao());
            db.setUrlImageCapa(cidade.getUrlImageCapa());
            db.setUrlImageCard(cidade.getUrlImageCard());
            db.setUrlDiscord(cidade.getUrlDiscord());
            db.setUrlInstagram(cidade.getUrlInstagram());
            db.setUrlTwitter(cidade.getUrlTwitter());

            cidadeRepository.save(db);

            return CidadeDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }


    public CidadeDTO votacao(Long id){
        Assert.notNull(id, "Não foi possível efetuar a votação");

        Optional<Cidade> optional = cidadeRepository.findById(id);

        if(optional.isPresent()){

            Cidade db = optional.get();
            db.setCoracao(db.getCoracao() + 5);
            cidadeRepository.save(db);
            return CidadeDTO.create(db);
            
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    // ---------------------Delete--------------------------------------
    @Transactional
    public ResponseEntity delete(Long id){

        if(cidadeRepository.findById(id).isPresent()){

            cidadeRepository.deleteCidadeStreamer(id);
            cidadeRepository.deleteCidadeGrupo(id);
            cidadeRepository.deleteById(id);

            if(cidadeRepository.findById(id).isPresent()){
                return ResponseEntity.unprocessableEntity().body("Failed to Delete the specified User");
            }else{
                return ResponseEntity.ok().body("Successfully deleted the specified user");
            }
        }else{
            return ResponseEntity.badRequest().body("Cannot find the user specified");
        }
    }

}
