package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Noticia;
import com.samaniasoft.toproleplay.dto.NoticiaDTO;
import com.samaniasoft.toproleplay.service.NoticiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/noticia")
public class NoticiaController {

    @Autowired
    NoticiaService noticiaService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getNoticias(Pageable pageable){
        return ResponseEntity.ok(noticiaService.getNoticias(pageable));
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarNoticia(@RequestBody Noticia noticia) {

        NoticiaDTO p = noticiaService.insert(noticia);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
