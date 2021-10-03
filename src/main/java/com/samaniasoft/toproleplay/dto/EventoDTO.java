package com.samaniasoft.toproleplay.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.List;
import javax.persistence.OneToMany;
import com.samaniasoft.toproleplay.domain.Evento;


@Data
public class EventoDTO {

    private Long id;
    private String titulo;
    private String urlVideo1;
    private String urlVideo2;
    private String urlVideo3;
    private String urlVideo4;
    private String urlImgCapa;
    private String urlImgCard;

    @OneToMany(mappedBy="eventoLadoA")
    private List<StreamerDTO> streamersLadoA;

    @OneToMany(mappedBy="eventoLadoB")
    private List<StreamerDTO> streamersLadoB;


    public static EventoDTO create(Evento player) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(player, EventoDTO.class);
    }
    
}
