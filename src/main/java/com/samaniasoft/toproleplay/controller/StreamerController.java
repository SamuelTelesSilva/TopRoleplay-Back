package com.samaniasoft.toproleplay.controller;

import java.net.URI;
import java.util.List;

import com.samaniasoft.toproleplay.domain.Streamer;
import org.springframework.data.domain.Sort;
import com.samaniasoft.toproleplay.dto.StreamerDTO;
import com.samaniasoft.toproleplay.service.StreamerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/streamer")
public class StreamerController {
    
    @Autowired
    StreamerService streamerService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getStreamers(Pageable pageable){
        return ResponseEntity.ok(streamerService.getStreamers(pageable));
    }


    @GetMapping("/search/{nome}")
    public ResponseEntity getStreamerByName(@PathVariable("nome") String nome,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "5") Integer size){
        List<StreamerDTO> streamers = streamerService.getStreamerByName(nome, PageRequest.of(page, size, Sort.by("id").descending()));

        return streamers.isEmpty() ? ResponseEntity.noContent().build() : 
                ResponseEntity.ok(streamers);
    }


    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarStreamer(@RequestBody Streamer streamer) {

        StreamerDTO p = streamerService.insert(streamer);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }


}
