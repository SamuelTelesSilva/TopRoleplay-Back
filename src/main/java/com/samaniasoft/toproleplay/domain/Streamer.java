package com.samaniasoft.toproleplay.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Streamer{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    
    @ManyToMany(mappedBy = "streamersDaCidade")//ok
    private List<Cidade> cidade;

    @JsonIgnore
    @OneToMany(mappedBy = "streamer")
    private List<Clipe> clipes;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="eventoLadoA_id")
    private Evento eventoLadoA;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="eventoLadoB_id")
    private Evento eventoLadoB;
    
        
}
//@OneToMany(mappedBy = "streamer")

/* @JoinTable(
    name = "GrupoStreamers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_streamer", "id_grupo"}),
    joinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id")
)*/