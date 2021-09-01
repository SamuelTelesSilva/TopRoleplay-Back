package com.samaniasoft.toproleplay.dto;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.samaniasoft.toproleplay.domain.Clipe;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class ClipeDTO {

    private Long id;
    private String titulo;
    private String url;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    private boolean twitch;

    @ManyToOne
    @JoinColumn(name = "streamer_id")
    private StreamerDTO streamer;
    

    public static ClipeDTO create(Clipe clipe) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clipe, ClipeDTO.class);
    }

}

/*Certo
    @JsonBackReference
    @ManyToOne
    private StreamerDTO streamer;

*/
