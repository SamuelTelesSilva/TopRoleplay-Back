package com.samaniasoft.toproleplay.dto;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.samaniasoft.toproleplay.domain.Cidade;
import com.samaniasoft.toproleplay.domain.Clipe;
import com.samaniasoft.toproleplay.domain.Grupo;
import com.samaniasoft.toproleplay.domain.RedeSocial;
import com.samaniasoft.toproleplay.domain.Streamer;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class StreamerDTO {

    private Long id;
    private String nome;

    @ManyToMany(mappedBy = "streamers")
    private List<RedeSocial> redeSociaisDoStreamer;

    @JsonIgnore
    @ManyToMany(mappedBy = "lideresGrupo")
    private List<Grupo> grupos;

    @ManyToMany(mappedBy = "streamersDaCidade")
    private List<Cidade> cidade;

    @JsonIgnore
    @OneToMany(mappedBy = "streamer")
    private List<Clipe> clipes;

    
    @JsonIgnore
    @ManyToMany(mappedBy = "membros")
    private List<Grupo> participa;

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