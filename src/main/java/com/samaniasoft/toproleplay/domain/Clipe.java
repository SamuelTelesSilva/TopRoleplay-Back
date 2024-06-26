package com.samaniasoft.toproleplay.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Clipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String url;
    private int coracao;
    private String urlImageCapa;
    private String urlImageCard;
    private boolean twitch;
    
    @ManyToOne
    @JoinColumn(name = "streamer_id", nullable = false)
    private Streamer streamer;
}

/*
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "streamer_id", nullable = false)
*/