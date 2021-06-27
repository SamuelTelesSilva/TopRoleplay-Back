package com.samaniasoft.toproleplay.controller;

import java.net.URI;
import java.util.List;

import com.samaniasoft.toproleplay.domain.Usuario;
import com.samaniasoft.toproleplay.dto.UsuarioDTO;
import com.samaniasoft.toproleplay.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    // ---------------------GET--------------------------------------
    @GetMapping()
    public ResponseEntity get() {
        List<UsuarioDTO> list = usuarioService.getUsers();
        return ResponseEntity.ok(list);
    }

    

    // ---------------------Post--------------------------------------
    @PostMapping
    public ResponseEntity post(@RequestBody Usuario user) {

        UsuarioDTO u = usuarioService.insert(user);

        URI location = getUri(u.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }


    // ---------------------Put--------------------------------------
    @PutMapping("/{id}")
    @Secured({"ROLE_USER"})
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        UsuarioDTO c = usuarioService.update(usuario, id);


        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }


}
