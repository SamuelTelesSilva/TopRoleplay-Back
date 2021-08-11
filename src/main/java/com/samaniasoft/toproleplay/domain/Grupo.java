package com.samaniasoft.toproleplay.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String genero;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    

    @ManyToMany
    @JoinTable(
        name = "Grupo_Lideres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_streamer", "id_grupo"}),
        joinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id")
    )
    private List<Streamer> lideresGrupo;

    @ManyToMany
    @JoinTable(
        name = "Grupo_Membros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_streamer", "id_grupo"}),
        joinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_streamer", referencedColumnName = "id")
        
        
        )
    private List<Streamer> membros;


    @ManyToMany(mappedBy = "gruposDaCidade")
    private List<Cidade> cidades;


}
