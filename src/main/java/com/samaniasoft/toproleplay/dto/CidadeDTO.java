package com.samaniasoft.toproleplay.dto;

import com.samaniasoft.toproleplay.domain.Cidade;
import org.modelmapper.ModelMapper;
import lombok.Data;

@Data
public class CidadeDTO {

    private Long id;
    private String nome;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    private String urlInstagram;
    private String urlTwitter;
    private String urlDiscord;
    //private List<RedeSocial> redeSociaisDaCidad;
    //private List<Streamer> streamersDaCidade;
    //private List<Grupo> gruposDaCidade;


    public static CidadeDTO create(Cidade cidade) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cidade, CidadeDTO.class);
    }

}
