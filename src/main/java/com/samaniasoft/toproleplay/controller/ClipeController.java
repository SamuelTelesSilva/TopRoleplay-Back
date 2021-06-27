package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Clipe;
import com.samaniasoft.toproleplay.dto.ClipeDTO;
import com.samaniasoft.toproleplay.service.ClipeService;

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
@RequestMapping("/api/clipe")
public class ClipeController {
    
    @Autowired
    ClipeService clipeService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getClipes(Pageable pageable){
        return ResponseEntity.ok(clipeService.getClipes(pageable));
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarClipe(@RequestBody Clipe clipe) {

        ClipeDTO p = clipeService.insert(clipe);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
    
}
