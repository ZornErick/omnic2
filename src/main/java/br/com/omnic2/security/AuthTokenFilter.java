package br.com.omnic2.security;

import br.com.omnic2.security.token.TokenService;
import br.com.omnic2.usuario.Usuario;
import br.com.omnic2.usuario.UsuarioRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepo usuarioRepo;

    public AuthTokenFilter(TokenService tokenService, UsuarioRepo usuarioRepo) {
        this.tokenService = tokenService;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        boolean valid = tokenService.isTokenValid(token);
        if(valid) {
            authClient(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authClient(String token) {
        Long idUsuario = tokenService.getId(token);
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(idUsuario);

        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getPerfis());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}
