package com.generation.rhcorp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rhcorp.model.Usuario;
import com.generation.rhcorp.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Usuario> usuario = userRepository.findByUsuario(userName);
        if (usuario.isPresent()) {
            return new UserDetailsImpl(usuario.get());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não encontrado!");
        }
    }

}
