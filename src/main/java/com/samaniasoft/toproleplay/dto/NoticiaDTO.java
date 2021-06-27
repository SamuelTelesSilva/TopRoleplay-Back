package com.samaniasoft.toproleplay.dto;

import com.samaniasoft.toproleplay.domain.Noticia;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class NoticiaDTO {

    private Long id;
    private String titulo;
    private String urlCapa;
    private String conteudo;

    public static NoticiaDTO create(Noticia noticia) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(noticia, NoticiaDTO.class);
    }
}
