package br.com.omnic2.produto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoUpdateForm {

    @NotBlank @Length(min = 3, max = 255)
    private String nome;
    @NotBlank @Length(min = 5, max = 500)
    private String descricao;
    @NotNull @DecimalMin("1")
    private BigDecimal preco;

    public ProdutoUpdateForm() {}

    public ProdutoUpdateForm(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Produto update(Produto produto) {
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);

        return produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
