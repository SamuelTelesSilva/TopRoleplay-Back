package com.samaniasoft.toproleplay.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import com.samaniasoft.toproleplay.domain.PlayerVideo;


@Data
public class PlayerVideoDTO {

    private Long id;
    private String titulo;
    private String urlVideo1;
    private String urlVideo2;
    private String urlVideo3;
    private String urlVideo4;
    private String urlImgCapa;
    private String urlImgCard;


    public static PlayerVideoDTO create(PlayerVideo player) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(player, PlayerVideoDTO.class);
    }
    
}
