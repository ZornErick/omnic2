package br.com.omnic2.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepo produtoRepo;

    @GetMapping
    public Page<ProdutoDto> readAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Produto> produtos = produtoRepo.findAll(pageable);
        return new ProdutoDto().convert(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> readById(@PathVariable("id") Long id) {
        Optional<Produto> produtoOptional = produtoRepo.findById(id);
        if(produtoOptional.isPresent()) {
            return ResponseEntity.ok(new ProdutoDto(produtoOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> create(@RequestBody @Valid ProdutoForm produtoForm, UriComponentsBuilder uriBuilder) {
        Produto produto = produtoForm.convert();
        produtoRepo.save(produto);

        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> update(@PathVariable("id") Long id, @RequestBody @Valid ProdutoUpdateForm produtoUpdateForm) {
        Optional<Produto> produtoOptional = produtoRepo.findById(id);
        if(produtoOptional.isPresent()) {
            Produto produto = produtoUpdateForm.update(produtoOptional.get());
            return ResponseEntity.ok(new ProdutoDto(produto));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Produto> produtoOptional = produtoRepo.findById(id);
        if(produtoOptional.isPresent()) {
            produtoRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
