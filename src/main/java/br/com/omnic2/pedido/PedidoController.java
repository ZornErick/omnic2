package br.com.omnic2.pedido;

import br.com.omnic2.produto.ProdutoRepo;
import br.com.omnic2.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepo pedidoRepo;

    @Autowired
    private ProdutoRepo produtoRepo;

    /*@GetMapping
    public Page<PedidoDto> readAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Pedido> pedidos = pedidoRepo.findAll(pageable);
        return new PedidoDto().convert(pedidos);
    }*/

    // ROLE_CLIENTE
    @GetMapping
    public Page<PedidoDto> readByUser(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Pedido> pedidos = pedidoRepo.findByUsuario_Id(usuario.getId(), pageable);
        return new PedidoDto().convert(pedidos);
    }

    // ROLE_ADMINISTRADOR
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> readById(@PathVariable("id") Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepo.findById(id);
        if(pedidoOptional.isPresent()) {
            return ResponseEntity.ok(new PedidoDto(pedidoOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // ROLE_CLIENTE
    @PostMapping
    @Transactional
    public ResponseEntity<PedidoDto> create(@RequestBody @Valid PedidoForm pedidoForm, UriComponentsBuilder uriBuilder) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Pedido> pedidoOptional = pedidoForm.convert(produtoRepo, usuario);
        if(pedidoOptional.isPresent()) {
            pedidoRepo.save(pedidoOptional.get());

            URI uri = uriBuilder.path("/pedidos/id").buildAndExpand(pedidoOptional.get().getId()).toUri();
            return ResponseEntity.created(uri).body(new PedidoDto(pedidoOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // ROLE_CLIENTE
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PedidoDto> update(@PathVariable("id") Long id, @RequestBody @Valid PedidoUpdateForm pedidoUpdateForm) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Pedido> pedidoOptional = pedidoRepo.findById(id);
        if(pedidoOptional.isPresent()) {
            if(Objects.equals(pedidoOptional.get().getUsuario().getId(), usuario.getId())) {
                Pedido pedido = pedidoUpdateForm.update(pedidoOptional.get(), produtoRepo);
                return ResponseEntity.ok(new PedidoDto(pedido));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    // ROLE_CLIENTE
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Pedido> pedidoOptional = pedidoRepo.findById(id);
        if(pedidoOptional.isPresent()) {
            if(Objects.equals(pedidoOptional.get().getUsuario().getId(), usuario.getId())) {
                pedidoRepo.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
