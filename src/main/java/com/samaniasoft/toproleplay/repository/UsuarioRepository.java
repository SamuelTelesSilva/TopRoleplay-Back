package com.samaniasoft.toproleplay.repository;

import com.samaniasoft.toproleplay.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByEmail(String email);
}
