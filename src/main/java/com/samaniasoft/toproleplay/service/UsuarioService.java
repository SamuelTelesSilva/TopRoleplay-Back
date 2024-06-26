package com.samaniasoft.toproleplay.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samaniasoft.toproleplay.domain.Usuario;
import com.samaniasoft.toproleplay.dto.UsuarioDTO;
import com.samaniasoft.toproleplay.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UsuarioService {

    private String senhaDB;
    private String senhaUser;
    //variavel que recebe a senha anterior
    private String senhaAnteriorBancoDados;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<UsuarioDTO> getUsers() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::create).collect(Collectors.toList());
    }

    public UsuarioDTO insert(Usuario user) {
        Assert.isNull(user.getId(), "Não foi possivel inserir o registro");
        return UsuarioDTO.createUser(usuarioRepository.save(user));
    }

    public UsuarioDTO update(Usuario user, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o usuario");

        // Busca o usuario no banco de dados
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if(optional.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
        
            Usuario db = optional.get();
            
            //Estou pegando a senha atual do banco de dados e colocando nesta variavel;
            senhaAnteriorBancoDados = db.getPassword();

            //Estou comparando as duas Strings/senhas, se a senha do banco for igual a senha digitada pelo usuario
            if(encoder.matches(user.getSenhaAnterior(), senhaAnteriorBancoDados)){
                db.setNome(user.getNome());
                db.setIdade(user.getIdade());
                db.setSenha(user.getPassword());

                // Atualiza o usuario
                usuarioRepository.save(db);

                return UsuarioDTO.create(db);
            }else{
                System.out.println("As senhas não bateram");
            }

            return null;
        } else {
            return null;
        }
    }


    /** Delete an User*/
    public ResponseEntity<Object> deleteUser(Long id) {
        if (usuarioRepository.findById(id).isPresent()) {
            usuarioRepository.deleteById(id);
            if (usuarioRepository.findById(id).isPresent())
                return ResponseEntity.unprocessableEntity().body("Failed to Delete the specified User");
            else return ResponseEntity.ok().body("Successfully deleted the specified user");
        } else return ResponseEntity.badRequest().body("Cannot find the user specified");
    }


    
    public UsuarioDTO updateAvatar(Usuario user, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o usuario");

        // Busca o usuario no banco de dados
        Optional<Usuario> optional = usuarioRepository.findById(id);
        
        if(optional.isPresent()) {
            Usuario db = optional.get();

            senhaDB = db.getPassword(); //pegando a senha atual do bd
            senhaUser = user.getSenhaProvisoria(); //pegando a senha que o front enviou

            //Estou comparando a senha do BD com a senha enviada pelo front
            //Se for true ele atualiza o avatar
            if(senhaDB.equals(senhaUser)){
                db.setUrlAvatar(user.getUrlAvatar());
                usuarioRepository.save(db);

                return UsuarioDTO.create(db);
            }else{
                return null;
            }

        } else {
            return null;
        }
    }
}
