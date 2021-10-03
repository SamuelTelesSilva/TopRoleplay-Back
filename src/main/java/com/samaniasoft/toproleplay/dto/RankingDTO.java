package com.samaniasoft.toproleplay.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
    

    @OneToMany(mappedBy="rankingPvp")
    private List<StreamerDTO> melhoresPvp;

    @OneToMany(mappedBy="rankingRP")
    private List<StreamerDTO> melhoresRp;

    public static RankingDTO create(Ranking ranking) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ranking, RankingDTO.class);
    }

}
