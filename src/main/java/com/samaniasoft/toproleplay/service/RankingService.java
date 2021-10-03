package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Ranking;
import com.samaniasoft.toproleplay.dto.RankingDTO;
import com.samaniasoft.toproleplay.repository.RankingRepository;
import com.samaniasoft.toproleplay.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RankingService {

    @Autowired
    RankingRepository rankingRepository;


    // ---------------------GET--------------------------------------
    public Page<Ranking> getRanking(Pageable pageable){
        return rankingRepository.findAll(pageable);
    }

    /*
    public Page<Ranking> getRankingTitle(String titulo, Pageable pageable){
        return rankingRepository.searchByTituloLike(titulo, pageable);
    }
    */

    public RankingDTO getRankingById(Long id){
        Optional<Ranking> ranking = rankingRepository.findById(id);
        return ranking.map(RankingDTO::create).orElseThrow(() -> new ObjectNotFoundException("Ranking não encontrado"));
    }

    // ---------------------Post------------------------------------
    public RankingDTO insert(Ranking ranking) {
        Assert.isNull(ranking.getId(), "Não foi possivel inserir o Ranking");
        return RankingDTO.create(rankingRepository.save(ranking));
    }

    // ---------------------Update------------------------------------
    public RankingDTO update(Ranking ranking, Long id){
        Assert.notNull(id, "Não foi possível atualizar o Ranking");

        Optional<Ranking> optional = rankingRepository.findById(id);

        if(optional.isPresent()){
            Ranking db = optional.get();

            db.setTitulo(ranking.getTitulo());
            db.setUrlImgCapa(ranking.getUrlImgCapa());
            db.setUrlImgCard(ranking.getUrlImgCard());
            db.setDescricao(ranking.getDescricao());


            rankingRepository.save(db);
            return RankingDTO.create(db);
        }else{
            return null;
        }
    }

    // ---------------------Delete------------------------------------
    public void delete(Long id){
        rankingRepository.deleteById(id);
    }
}
