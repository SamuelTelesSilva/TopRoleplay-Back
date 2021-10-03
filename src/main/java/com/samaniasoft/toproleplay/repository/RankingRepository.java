package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Ranking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long>{
    
}
