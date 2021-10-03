package com.samaniasoft.toproleplay.dto;

import javax.persistence.Entity;
import com.samaniasoft.toproleplay.domain.Ranking;
import org.modelmapper.ModelMapper;
import lombok.Data;

@Data
public class RankingDTO {

    private Long id;
    private String titulo;
    private String descricao;
    //private Integer posicao;
    private String urlImgCapa;
    private String urlImgCard;
    

    public static RankingDTO create(Ranking ranking) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ranking, RankingDTO.class);
    }

}
