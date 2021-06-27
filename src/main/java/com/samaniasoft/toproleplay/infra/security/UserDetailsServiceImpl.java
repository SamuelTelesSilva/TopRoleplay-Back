package com.samaniasoft.toproleplay.infra.security;




import com.samaniasoft.toproleplay.domain.Usuario;
import com.samaniasoft.toproleplay.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = userRep.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return user;

    }
}
