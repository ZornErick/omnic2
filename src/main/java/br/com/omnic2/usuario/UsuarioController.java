package br.com.omnic2.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @GetMapping
    public List<UsuarioDto> readAll() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return new UsuarioDto().convert(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> readById(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(new UsuarioDto(usuarioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDto> create(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioForm.convert();
        usuarioRepo.save(usuario);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioDto> update(@PathVariable("id") Long id, @RequestBody @Valid UsuarioUpdateForm usuarioUpdateForm) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioUpdateForm.update(usuarioOptional.get());
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
        if(usuarioOptional.isPresent()) {
            usuarioRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
