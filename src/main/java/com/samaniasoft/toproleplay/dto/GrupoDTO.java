package com.samaniasoft.toproleplay.dto;

import java.util.List;

import javax.persistence.ManyToMany;


import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.domain.RedeSocial;
import com.samaniasoft.toproleplay.domain.Streamer;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class GrupoDTO {
    
    private Long id;
    private String nome;
    private String genero;
    
    @ManyToMany
    private List<Streamer> liderGrupo;
    //private List<Streamer> membros;
    //private List<RedeSocial> redeSociais;
    //private List<Cidade> cidades;


    public static GrupoDTO create(Grupo grupo) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(grupo, GrupoDTO.class);
    }

}
