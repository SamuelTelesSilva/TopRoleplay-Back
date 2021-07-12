package com.samaniasoft.toproleplay.service;

import java.util.List;

import com.samaniasoft.toproleplay.domain.Streamer;
import com.samaniasoft.toproleplay.dto.StreamerDTO;
import com.samaniasoft.toproleplay.repository.StreamerRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class StreamerService {
    
    @Autowired
    StreamerRepository streamerRepository;


    // ---------------------GET--------------------------------------
    public Page<Streamer> getStreamers(Pageable pageable){
        return streamerRepository.findAll(pageable);
    }

    public List<StreamerDTO> getStreamerByName(String nome, Pageable pageable){
        return streamerRepository.findByNome(nome, pageable).stream().map(StreamerDTO::create).collect(Collectors.toList());
    }

    // ---------------------Post------------------------------------
    public StreamerDTO insert(Streamer streamer) {
        Assert.isNull(streamer.getId(), "Não foi possivel inserir o seu Post");
        return StreamerDTO.create(streamerRepository.save(streamer));
    }
}
