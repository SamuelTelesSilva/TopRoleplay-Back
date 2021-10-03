package com.samaniasoft.toproleplay.dto;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samaniasoft.toproleplay.domain.Cidade;
import com.samaniasoft.toproleplay.domain.Clipe;
import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.domain.Streamer;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class StreamerDTO {

    private Long id;
    private String nome;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    private String urlFacebook;
    private String urlInstagram;
    private String urlTwitter;
    private String urlPlataformaStream;

    @JsonIgnore
    @ManyToMany(mappedBy = "lideresGrupo")
    private List<Grupo> grupos;

    @ManyToMany(mappedBy = "streamersDaCidade")
    private List<Cidade> cidades;

    @JsonIgnore
    @OneToMany(mappedBy = "streamer")
    private List<Clipe> clipes;

    
    @JsonIgnore
    @ManyToMany(mappedBy = "membros")
    private List<Grupo> participa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="eventoLadoA_id")
    private EventoDTO eventoLadoA;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="eventoLadoB_id")
    private EventoDTO eventoLadoB;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="rankingPvp_id")
    private RankingDTO rankingPvp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="rankingRP_id")
    private RankingDTO rankingRP;


    public static StreamerDTO create(Streamer streamer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(streamer, StreamerDTO.class);
    }

    
}

/*Certo
   @JsonManagedReference
    @OneToMany(mappedBy = "streamer")
    private List<ClipeDTO> clipes; //possivel erro

*/