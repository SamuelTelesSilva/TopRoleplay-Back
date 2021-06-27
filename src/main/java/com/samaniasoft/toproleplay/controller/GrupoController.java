package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.dto.GrupoDTO;
import com.samaniasoft.toproleplay.service.GrupoService;

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
@RequestMapping("/api/grupo")
public class GrupoController {
    
    @Autowired
    GrupoService grupoService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getGrupos(Pageable pageable){
        return ResponseEntity.ok(grupoService.getGrupos(pageable));
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarGrupo(@RequestBody Grupo grupo) {

        GrupoDTO p = grupoService.insert(grupo);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
