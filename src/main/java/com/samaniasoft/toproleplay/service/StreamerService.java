package com.samaniasoft.toproleplay.service;

import java.util.List;
import java.util.Optional;

import com.samaniasoft.toproleplay.domain.Streamer;
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

    public List<StreamerDTO> getStreamerByName(String nome, Pageable pageable){
        return streamerRepository.findByNome(nome, pageable).stream().map(StreamerDTO::create).collect(Collectors.toList());
    }

    // ---------------------Post------------------------------------
    public StreamerDTO insert(Streamer streamer) {
        Assert.isNull(streamer.getId(), "Não foi possivel inserir o seu Post");
        return StreamerDTO.create(streamerRepository.save(streamer));
    }


    // ---------------------Put------------------------------------
    public StreamerDTO update(Streamer streamer, Long id) {

        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o streamer no banco de dados
        Optional<Streamer> optional = streamerRepository.findById(id);
        if(optional.isPresent()) {
            Streamer db = optional.get();
            
            System.out.println(db);
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
