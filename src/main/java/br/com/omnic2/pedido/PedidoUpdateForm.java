package br.com.omnic2.pedido;

import br.com.omnic2.pedido.item.ItemPedido;
import br.com.omnic2.produto.Produto;
import br.com.omnic2.produto.ProdutoRepo;

import java.util.Optional;

public class PedidoUpdateForm {

    private String status;
    private Long produtoId;
    private int produtoQtd;

    public PedidoUpdateForm() {}

    public PedidoUpdateForm(String status, Long produtoId, int produtoQtd) {
        this.status = status;
        this.produtoId = produtoId;
        this.produtoQtd = produtoQtd;
    }

    public Pedido update(Pedido pedido, ProdutoRepo produtoRepo) {
        if(produtoId != null) {
            Optional<Produto> produtoOptional = produtoRepo.findById(produtoId);
            if(produtoOptional.isPresent()) {
                ItemPedido item = new ItemPedido(produtoQtd, produtoOptional.get());
                pedido.adicionarItem(item);
            }
        }
        if(status != null) {
            pedido.setStatus(StatusPedido.valueOf(status));
        }
        return pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getProdutoQtd() {
        return produtoQtd;
    }

    public void setProdutoQtd(int produtoQtd) {
        this.produtoQtd = produtoQtd;
    }
}
