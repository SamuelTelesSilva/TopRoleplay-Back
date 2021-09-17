package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.PlayerVideo;
import com.samaniasoft.toproleplay.dto.PlayerVideoDTO;
import com.samaniasoft.toproleplay.service.PlayerVideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/api/playervideo")
public class PlayerVideoController {
    
    @Autowired
    PlayerVideoService playerVideoService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getLinkPlayer(Pageable pageable){
        return ResponseEntity.ok(playerVideoService.getLinkPlayer(pageable));
    }


    @GetMapping("/search/{titulo}")
    public ResponseEntity searchPlayerByTitle(
        @PathVariable("titulo") String titulo, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(playerVideoService.getLinkPlayerTitle(titulo, PageRequest.of(page, size, Sort.by("id").descending())));
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarPlayer(@RequestBody PlayerVideo player) {

        PlayerVideoDTO p = playerVideoService.insert(player);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    // ---------------------Update--------------------------------------
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity updatePlayer(@PathVariable("id") Long id, @RequestBody PlayerVideo player){
        PlayerVideoDTO c = playerVideoService.update(player, id);

        return c != null ?
            ResponseEntity.ok(c) :
            ResponseEntity.notFound().build();
    }
    

    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity delete(@PathVariable("id") Long id){
        playerVideoService.delete(id);
        return ResponseEntity.ok().build();
    }

    
}