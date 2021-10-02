package com.samaniasoft.toproleplay.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String urlVideo1;
    private String urlVideo2;
    private String urlVideo3;
    private String urlVideo4;
    private String urlImgCapa;
    private String urlImgCard;
    
}
