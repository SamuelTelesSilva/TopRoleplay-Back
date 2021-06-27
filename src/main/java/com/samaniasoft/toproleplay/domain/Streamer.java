package com.samaniasoft.toproleplay.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;


import lombok.Data;

@Entity
@Data
public class Streamer{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int coracao;

    
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
    
}
//@OneToMany(mappedBy = "streamer")

/* @JoinTable(
    name = "GrupoStreamers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_streamer", "id_grupo"}),
    joinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id")
)*/