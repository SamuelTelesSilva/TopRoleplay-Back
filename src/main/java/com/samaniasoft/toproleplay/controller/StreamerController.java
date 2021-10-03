package com.samaniasoft.toproleplay.controller;

import java.net.URI;


import com.samaniasoft.toproleplay.domain.Streamer;
import org.springframework.data.domain.Sort;
import com.samaniasoft.toproleplay.dto.StreamerDTO;
import com.samaniasoft.toproleplay.service.StreamerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    //top 10
    @GetMapping("/top")
    public ResponseEntity getTopStreamers(
        Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(streamerService.getTopStreamers(PageRequest.of(page, size, Sort.by("coracao").descending())));
    }

    @GetMapping("/search/{nome}")
    public ResponseEntity searchStreamerByName(
        @PathVariable("nome") String nome, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(streamerService.getStreamerByNameLike(nome, PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @GetMapping("/{id}")
    public ResponseEntity getStreamerById(@PathVariable("id") Long id){
        StreamerDTO streamer = streamerService.getStreamerById(id);
        return ResponseEntity.ok(streamer);
    }


    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarStreamer(@RequestBody Streamer streamer) {

        StreamerDTO p = streamerService.insert(streamer);
        System.out.println(p.getId());

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }


    @PostMapping("/cidadeid/{id_cidade}/streamerid/{id_streamer}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity saveCityStreamers(
        @PathVariable("id_cidade") Long id_cidade, 
        @PathVariable("id_streamer") Long id_streamer
        ){
        streamerService.insertCityStreamer(
            id_cidade, 
            id_streamer);
        return ResponseEntity.ok().build();
    }

    // ---------------------Put--------------------------------------
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Streamer streamer) {
        streamer.setId(id);
        StreamerDTO c = streamerService.update(streamer, id);

        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    
    //Atualizando o cidade_streamer
    @PutMapping(
        "/newcity/{idCidadeNew}/newstreamer/{idStreamerNew}/cidade/{idCidadeAtual}/streamer/{idStreamerAtual}"
    )
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity updateCityStreamers(
        @PathVariable("idCidadeNew") Long idCidadeNew, 
        @PathVariable("idStreamerNew") Long idStreamerNew,
        @PathVariable("idCidadeAtual") Long idCidadeAtual, 
        @PathVariable("idStreamerAtual") Long idStreamerAtual
        ){
        streamerService.updateCidadeStreamers(
            idCidadeNew, 
            idStreamerNew, 
            idCidadeAtual, 
            idStreamerAtual);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/votar/{id}")
    public ResponseEntity votacaoCoracao(@PathVariable("id") Long id) {
        StreamerDTO s = streamerService.votacao(id);

        return s != null ?
                ResponseEntity.ok(s) :
                ResponseEntity.notFound().build();
    }
    


    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity delete(@PathVariable("id") Long id){
        streamerService.delete(id);
        return ResponseEntity.ok().build();
    }


   
    
    

}
