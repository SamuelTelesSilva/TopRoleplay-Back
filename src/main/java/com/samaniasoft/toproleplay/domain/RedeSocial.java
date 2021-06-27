package com.samaniasoft.toproleplay.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class RedeSocial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String urlDaRedeSocial;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "redeSocial_cidade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_redeSocial", "id_cidade"}),
        joinColumns = @JoinColumn(name = "id_redeSocial", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_cidade", referencedColumnName = "id")
    )
    private List<Cidade> cidades;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "redeSocial_streamer",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_redeSocial", "id_streamer"}),
        joinColumns = @JoinColumn(name = "id_redeSocial", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id")
    )
    private List<Streamer> streamers;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "redeSocial_grupo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_redeSocial", "id_grupo"}),
        joinColumns = @JoinColumn(name = "id_redeSocial", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    )
    private List<Grupo> grupos;

}
