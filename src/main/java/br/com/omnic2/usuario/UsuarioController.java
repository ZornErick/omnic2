package br.com.omnic2.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PerfilRepo perfilRepo;

    /*@GetMapping
    public List<UsuarioDto> readAll() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return new UsuarioDto().convert(usuarios);
    }*/

    // ROLE_CLIENTE
    @GetMapping
    public ResponseEntity<UsuarioDto> readByUser() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(usuario.getId());
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(new UsuarioDto(usuarioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // ROLE_ADMINISTRADOR
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> readById(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(new UsuarioDto(usuarioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // ROLE_CLIENTE
    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDto> create(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioForm.convert();
        Optional<Perfil> perfilOptional = perfilRepo.findByNome("ROLE_CLIENTE");
        if(perfilOptional.isPresent()) {
            usuario.setPerfil(perfilOptional.get());
        }
        usuarioRepo.save(usuario);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
    }

    // ROLE_CLIENTE
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioDto> update(@PathVariable("id") Long id, @RequestBody @Valid UsuarioUpdateForm usuarioUpdateForm) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            if(Objects.equals(usuarioOptional.get().getId(), usuario.getId())) {
                Usuario usuarioUpdate = usuarioUpdateForm.update(usuarioOptional.get());
                return ResponseEntity.ok(new UsuarioDto(usuarioUpdate));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    /*// ROLE_CLIENTE
    // CASCADE_TYPE
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            if(Objects.equals(usuarioOptional.get().getId(), usuario.getId())) {
                usuarioRepo.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }*/
}
