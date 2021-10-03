package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.dto.GrupoDTO;
import com.samaniasoft.toproleplay.service.GrupoService;
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
import org.springframework.data.domain.Sort;

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


    @GetMapping("/top")
    public ResponseEntity topGroup(
        Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(grupoService.getTopGrupos(PageRequest.of(page, size, Sort.by("coracao").descending())));
    }


    @GetMapping("/search/{nome}")
    public ResponseEntity searchGroupByName(
        @PathVariable("nome") String nome, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(grupoService.getGroupByNameLike(nome, PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @GetMapping("/{id}")
    public ResponseEntity getGrupoById(@PathVariable("id") Long id){
        return ResponseEntity.ok(grupoService.getGroupById(id));
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity getGrupoById(@PathVariable("id") Long id){
        GrupoDTO grupo = grupoService.getGroupById(id);
        return ResponseEntity.ok(grupo);
    }
    */

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
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity saveLeaderGroup(
        @PathVariable("id") Long id, 
        @PathVariable("idStreamer") Long idStreamer
    ){
        grupoService.insertLeaderGroup(id, idStreamer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/membro/{idStreamer}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity saveMemberGroup(
        @PathVariable("id") Long id, 
        @PathVariable("idStreamer") Long idStreamer
    ){
        grupoService.insertMemberGroup(id, idStreamer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cidade/{idCidade}")
    @Secured({"ROLE_ADMIN"})
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

    @PutMapping("/votar/{id}")
    public ResponseEntity putVotacao(@PathVariable("id") Long id) {

        GrupoDTO g = grupoService.votacao(id);

        return g != null ?
                ResponseEntity.ok(g) :
                ResponseEntity.notFound().build();
    }

    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity deleteAllGrupo(@PathVariable("id") Long id){
        grupoService.deleteAllByGroupId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id_grupo}/lider/{id_streamer}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity deleteLeader(
        @PathVariable("id_grupo") Long id_grupo, @PathVariable("id_streamer") Long id_streamer
    ){
        grupoService.deleteLeaderGroup(id_grupo, id_streamer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id_grupo}/membro/{id_streamer}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity deleteMember(
        @PathVariable("id_grupo") Long id_grupo, @PathVariable("id_streamer") Long id_streamer
    ){
        grupoService.deleteMemberGroup(id_grupo, id_streamer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id_grupo}/cidade/{id_cidade}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity deleteCity(
        @PathVariable("id_grupo") Long id_grupo, @PathVariable("id_cidade") Long id_cidade
    ){
        grupoService.deleteCityGroup(id_cidade, id_grupo);
        return ResponseEntity.ok().build();
    }


}
