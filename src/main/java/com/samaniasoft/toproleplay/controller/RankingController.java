package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Ranking;
import com.samaniasoft.toproleplay.dto.RankingDTO;
import com.samaniasoft.toproleplay.service.RankingService;

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
@RequestMapping("/api/ranking")
public class RankingController {

    @Autowired
    RankingService rankingService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getRanking(Pageable pageable){
        return ResponseEntity.ok(rankingService.getRanking(pageable));
    }

    /*
    @GetMapping("/search/{titulo}")
    public ResponseEntity searchEventoByTitle(
        @PathVariable("titulo") String titulo, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(rankingService.getLinkEventoTitle(titulo, PageRequest.of(page, size, Sort.by("id").descending())));
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity getRankingById(@PathVariable("id") Long id){
        RankingDTO ranking = rankingService.getRankingById(id);
        return ResponseEntity.ok(ranking);
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarRanking(@RequestBody Ranking ranking) {

        RankingDTO p = rankingService.insert(ranking);

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
    public ResponseEntity updateRanking(@PathVariable("id") Long id, @RequestBody Ranking ranking){
        RankingDTO c = rankingService.update(ranking, id);

        return c != null ?
            ResponseEntity.ok(c) :
            ResponseEntity.notFound().build();
    }
    

    // ---------------------Delete--------------------------------------
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity delete(@PathVariable("id") Long id){
        rankingService.delete(id);
        return ResponseEntity.ok().build();
    }
}
