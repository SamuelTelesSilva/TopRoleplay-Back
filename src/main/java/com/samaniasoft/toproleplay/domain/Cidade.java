package com.samaniasoft.toproleplay.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class Cidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    private String urlInstagram;
    private String urlTwitter;
    private String urlDiscord;



    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "cidade_grupos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_cidade", "id_grupo"}),
        joinColumns = @JoinColumn(name = "id_cidade", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id")
        )
    private List<Grupo> gruposDaCidade;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "cidade_streamers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_cidade", "id_streamer"}),
        joinColumns = @JoinColumn(name = "id_cidade", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id")
    )
    private List<Streamer> streamersDaCidade;


}
