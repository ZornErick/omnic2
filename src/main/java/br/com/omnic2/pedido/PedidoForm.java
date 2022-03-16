package br.com.omnic2.pedido;

import br.com.omnic2.pedido.item.ItemPedido;
import br.com.omnic2.produto.Produto;
import br.com.omnic2.produto.ProdutoRepo;
import br.com.omnic2.usuario.Usuario;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class PedidoForm {

    @NotNull @DecimalMin("1")
    private Long produtoId;
    @NotNull @DecimalMin("1")
    private int produtoQtd;

    public PedidoForm() {}

    public PedidoForm(Long produtoId, int produtoQtd) {
        this.produtoId = produtoId;
        this.produtoQtd = produtoQtd;
    }

    public Optional<Pedido> convert(ProdutoRepo produtoRepo, Usuario usuario) {
        Optional<Produto> produtoOptional = produtoRepo.findById(produtoId);
        if(produtoOptional.isPresent()) {
            Pedido pedido = new Pedido(usuario);
            ItemPedido item = new ItemPedido(produtoQtd, produtoOptional.get());
            pedido.adicionarItem(item);

            return Optional.of(pedido);
        }
        return Optional.empty();
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public int getProdutoQtd() {
        return produtoQtd;
    }
}
