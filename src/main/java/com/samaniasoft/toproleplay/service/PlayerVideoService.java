package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.PlayerVideo;
import com.samaniasoft.toproleplay.dto.PlayerVideoDTO;
import com.samaniasoft.toproleplay.repository.PlayerVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PlayerVideoService {
    
    @Autowired
    PlayerVideoRepository PlayerVideoRepository;


    // ---------------------GET--------------------------------------
    public Page<PlayerVideo> getLinkPlayer(Pageable pageable){
        return PlayerVideoRepository.findAll(pageable);
    }

    public Page<PlayerVideo> getLinkPlayerTitle(String titulo, Pageable pageable){
        return PlayerVideoRepository.searchByTituloLike(titulo, pageable);
    }

    // ---------------------Post------------------------------------
    public PlayerVideoDTO insert(PlayerVideo player) {
        Assert.isNull(player.getId(), "Não foi possivel inserir o seu Player de Vídeo");
        return PlayerVideoDTO.create(PlayerVideoRepository.save(player));
    }

    // ---------------------Update------------------------------------
    public PlayerVideoDTO update(PlayerVideo player, Long id){
        Assert.notNull(id, "Não foi possível atualizar o Player");

        Optional<PlayerVideo> optional = PlayerVideoRepository.findById(id);

        if(optional.isPresent()){
            PlayerVideo db = optional.get();

            db.setTitulo(player.getTitulo());
            db.setUrlImgCapa(player.getUrlImgCapa());
            db.setUrlImgCard(player.getUrlImgCard());
            db.setUrlVideo1(player.getUrlVideo1());
            db.setUrlVideo2(player.getUrlVideo2());
            db.setUrlVideo3(player.getUrlVideo3());
            db.setUrlVideo4(player.getUrlVideo4());

            PlayerVideoRepository.save(db);
            return PlayerVideoDTO.create(db);
        }else{
            return null;
        }
    }

    // ---------------------Delete------------------------------------
    public void delete(Long id){
        PlayerVideoRepository.deleteById(id);
    }

}

