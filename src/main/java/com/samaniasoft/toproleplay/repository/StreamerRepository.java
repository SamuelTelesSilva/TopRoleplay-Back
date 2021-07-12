package com.samaniasoft.toproleplay.repository;

import java.util.List;

import com.samaniasoft.toproleplay.domain.Streamer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamerRepository extends JpaRepository<Streamer, Long>{
    List<Streamer> findByNome(String nome, Pageable pageable);
}
