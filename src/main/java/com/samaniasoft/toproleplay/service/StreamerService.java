package com.samaniasoft.toproleplay.service;

import java.util.List;
import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Streamer;
import com.samaniasoft.toproleplay.domain.cidadeStreamer;
import com.samaniasoft.toproleplay.dto.StreamerDTO;
import com.samaniasoft.toproleplay.dto.UsuarioDTO;
import com.samaniasoft.toproleplay.repository.StreamerRepository;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class StreamerService {
    
    @Autowired
    StreamerRepository streamerRepository;


    // ---------------------GET--------------------------------------
    public Page<Streamer> getStreamers(Pageable pageable){
        return streamerRepository.findAll(pageable);
    }

    public Page<Streamer> getStreamerByNameLike(String nome, Pageable pageable){
        return streamerRepository.searchByNameLike(nome, pageable);
    }

    // ---------------------Post------------------------------------
    public StreamerDTO insert(Streamer streamer) {
        Assert.isNull(streamer.getId(), "Não foi possivel inserir o seu Post");
        return StreamerDTO.create(streamerRepository.save(streamer));
    }

    @Transactional
    public void insertCityStreamer(Long id_cidade, Long id_streamer) {
        streamerRepository.saveCidadeStreamers(id_cidade, id_streamer);
    }

    // ---------------------Put------------------------------------
    @Transactional
    public StreamerDTO update(Streamer streamer, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o streamer no banco de dados
        Optional<Streamer> optional = streamerRepository.findById(id);

        if(optional.isPresent()) {
            Streamer db = optional.get();
            
            // Copiar as propriedades
            db.setNome(streamer.getNome());
            db.setCoracao(streamer.getCoracao());
            db.setUrlFacebook(streamer.getUrlFacebook());
            db.setUrlImageCapa(streamer.getUrlImageCapa());
            db.setUrlImageCard(streamer.getUrlImageCard());
            db.setUrlInstagram(streamer.getUrlInstagram());
            db.setUrlPlataformaStream(streamer.getUrlPlataformaStream());
            db.setUrlTwitter(streamer.getUrlTwitter());
            
            // Atualiza o streamer
            streamerRepository.save(db);

            return StreamerDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    /**
     * Update da associação cidade_streamer
     * @param idCidadeNew
     * @param idStreamerNew
     * @param idCidadeAtual
     * @param idStreamerAtual
     */
    @Transactional
    public void updateCidadeStreamers(Long idCidadeNew, Long idStreamerNew, Long idCidadeAtual, Long idStreamerAtual) {
        Assert.notNull(idStreamerAtual,"Não foi possível atualizar o registro");
        streamerRepository.updateCidadeStreamers(idCidadeNew, idStreamerNew, idCidadeAtual, idStreamerAtual);
    }


    // ---------------------Delete------------------------------------
    /**
     * Estou pegando os metodos personalizados com o @Query no repository,
     * para deletar o pai precisa retirar os filhos
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        streamerRepository.deleteGrupoMembros(id);
        streamerRepository.deleteGrupoLideres(id);
        streamerRepository.deleteCidadeStreamer(id);
        streamerRepository.deleteClipe(id);
        streamerRepository.deleteById(id);
    }

    

}
