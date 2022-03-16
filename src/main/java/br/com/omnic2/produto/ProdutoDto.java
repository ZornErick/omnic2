package br.com.omnic2.produto;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoDto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private LocalDateTime dataRegistro;

    public ProdutoDto() {}

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.dataRegistro = produto.getDataRegistro();
    }

    public Page<ProdutoDto> convert(Page<Produto> produtos) {
        return produtos.map(ProdutoDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
}
