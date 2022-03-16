package br.com.omnic2.security;

import br.com.omnic2.usuario.Usuario;
import br.com.omnic2.usuario.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepo.findByUsuario(username);
        if(usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
