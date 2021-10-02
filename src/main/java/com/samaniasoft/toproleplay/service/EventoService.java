package com.samaniasoft.toproleplay.service;

import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Evento;
import com.samaniasoft.toproleplay.dto.EventoDTO;
import com.samaniasoft.toproleplay.infra.exception.ObjectNotFoundException;
import com.samaniasoft.toproleplay.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
public class EventoService {
    
    @Autowired
    EventoRepository eventoRepository;


    // ---------------------GET--------------------------------------
    public Page<Evento> getLinkEvento(Pageable pageable){
        return eventoRepository.findAll(pageable);
    }

    public Page<Evento> getLinkEventoTitle(String titulo, Pageable pageable){
        return eventoRepository.searchByTituloLike(titulo, pageable);
    }

    public EventoDTO getEventoById(Long id){
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.map(EventoDTO::create).orElseThrow(() -> new ObjectNotFoundException("Evento não encontrado"));
    }

    // ---------------------Post------------------------------------
    public EventoDTO insert(Evento evento) {
        Assert.isNull(evento.getId(), "Não foi possivel inserir o seu Evento");
        return EventoDTO.create(eventoRepository.save(evento));
    }

    // ---------------------Update------------------------------------
    public EventoDTO update(Evento evento, Long id){
        Assert.notNull(id, "Não foi possível atualizar o Evento");

        Optional<Evento> optional = eventoRepository.findById(id);

        if(optional.isPresent()){
            Evento db = optional.get();

            db.setTitulo(evento.getTitulo());
            db.setUrlImgCapa(evento.getUrlImgCapa());
            db.setUrlImgCard(evento.getUrlImgCard());
            db.setUrlVideo1(evento.getUrlVideo1());
            db.setUrlVideo2(evento.getUrlVideo2());
            db.setUrlVideo3(evento.getUrlVideo3());
            db.setUrlVideo4(evento.getUrlVideo4());

            eventoRepository.save(db);
            return EventoDTO.create(db);
        }else{
            return null;
        }
    }

    // ---------------------Delete------------------------------------
    public void delete(Long id){
        eventoRepository.deleteById(id);
    }

}

