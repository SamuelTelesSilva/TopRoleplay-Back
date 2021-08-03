package com.samaniasoft.toproleplay.controller;

import java.net.URI;

import com.samaniasoft.toproleplay.domain.Cidade;
import com.samaniasoft.toproleplay.dto.CidadeDTO;
import com.samaniasoft.toproleplay.service.CidadeService;
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
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

    @Autowired
    CidadeService cidadeService;

    // ---------------------GET--------------------------------------
    @GetMapping
    public ResponseEntity getCidades(Pageable pageable){
        return ResponseEntity.ok(cidadeService.getCidades(pageable));
    }


    @GetMapping("/search/{nome}")
    public ResponseEntity searchCityByName(
        @PathVariable("nome") String nome, Pageable pageable,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        return ResponseEntity.ok(cidadeService.getCityByNameLike(nome, PageRequest.of(page, size, Sort.by("id").descending())));
    }

    // ---------------------Post--------------------------------------
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity adicionarCidades(@RequestBody Cidade cidade) {

        CidadeDTO p = cidadeService.insert(cidade);

        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
