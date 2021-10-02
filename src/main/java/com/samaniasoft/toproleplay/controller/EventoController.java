package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Evento;
import com.samaniasoft.toproleplay.dto.EventoDTO;
import com.samaniasoft.toproleplay.service.EventoService;

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
@RequestMapping("/api/evento")
public class EventoController {
    
    @Autowired
    EventoService eventoService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getLinkEvento(Pageable pageable){
        return ResponseEntity.ok(eventoService.getLinkEvento(pageable));
    }

    @GetMapping("/search/{titulo}")
    public ResponseEntity searchEventoByTitle(
        @PathVariable("titulo") String titulo, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(eventoService.getLinkEventoTitle(titulo, PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventoById(@PathVariable("id") Long id){
        EventoDTO evento = eventoService.getEventoById(id);
        return ResponseEntity.ok(evento);
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarEvento(@RequestBody Evento evento) {

        EventoDTO p = eventoService.insert(evento);

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
    public ResponseEntity updateEvento(@PathVariable("id") Long id, @RequestBody Evento evento){
        EventoDTO c = eventoService.update(evento, id);

        return c != null ?
            ResponseEntity.ok(c) :
            ResponseEntity.notFound().build();
    }
    

    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity delete(@PathVariable("id") Long id){
        eventoService.delete(id);
        return ResponseEntity.ok().build();
    }

    
}