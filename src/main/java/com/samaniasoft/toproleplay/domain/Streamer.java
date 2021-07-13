package com.samaniasoft.toproleplay.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ManyToMany;



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

    
    @ManyToMany(mappedBy = "streamersDaCidade")
    private List<Cidade> cidade;

    @JsonIgnore
    @OneToMany(mappedBy = "streamer")
    private List<Clipe> clipes;

    
    @JsonIgnore
    @ManyToMany(mappedBy = "membros")
    private List<Grupo> participa;


    
    
}
//@OneToMany(mappedBy = "streamer")

/* @JoinTable(
    name = "GrupoStreamers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_streamer", "id_grupo"}),
    joinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id")
)*/