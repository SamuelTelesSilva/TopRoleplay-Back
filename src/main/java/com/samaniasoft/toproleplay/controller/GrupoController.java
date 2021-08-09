package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.dto.GrupoDTO;
import com.samaniasoft.toproleplay.service.GrupoService;

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


    @PostMapping("/{id}/lider/{idStreamer}")
    public ResponseEntity saveLeaderGroup(
        @PathVariable("id") Long id, 
        @PathVariable("idStreamer") Long idStreamer
    ){
        grupoService.insertLeaderGroup(id, idStreamer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/membro/{idStreamer}")
    public ResponseEntity saveMemberGroup(
        @PathVariable("id") Long id, 
        @PathVariable("idStreamer") Long idStreamer
    ){
        grupoService.insertMemberGroup(id, idStreamer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cidade/{idCidade}")
    public ResponseEntity saveCityGroup(
        @PathVariable("id") Long id, 
        @PathVariable("idCidade") Long idCidade
    ){
        grupoService.insertCityGroup(idCidade, id);
        return ResponseEntity.ok().build();
    }


    // ---------------------Update--------------------------------------
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Grupo grupo) {

        GrupoDTO g = grupoService.update(grupo, id);

        return g != null ?
                ResponseEntity.ok(g) :
                ResponseEntity.notFound().build();
    }


    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAllGrupo(@PathVariable("id") Long id){
        grupoService.deleteAllByGroupId(id);
        return ResponseEntity.ok().build();
    }
}
