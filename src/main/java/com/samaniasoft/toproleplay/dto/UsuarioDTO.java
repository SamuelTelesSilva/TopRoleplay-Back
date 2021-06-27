package com.samaniasoft.toproleplay.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samaniasoft.toproleplay.domain.Role;
import com.samaniasoft.toproleplay.domain.Usuario;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String senhaAnterior;
    // token jwt
    private String token;
    private List<String> roles;
    private String urlAvatar;
    private int idade;

    public static UsuarioDTO create(Usuario user) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(user, UsuarioDTO.class);

        dto.roles = user.getRoles().stream()
            .map(Role::getNome)
            .collect(Collectors.toList());

        return dto;
    }

    public static UsuarioDTO create(Usuario user, String token) {
        UsuarioDTO dto = create(user);
        dto.token = token;
        return dto;
    }


    public static UsuarioDTO createUser(Usuario user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UsuarioDTO.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }

}
