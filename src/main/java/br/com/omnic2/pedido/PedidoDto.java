package br.com.omnic2.pedido;

import br.com.omnic2.pedido.item.ItemPedido;
import br.com.omnic2.produto.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDto {

    private Long id;
    private BigDecimal valorTotal;
    private LocalDateTime dataRegistro;
    private String status;
    private String usuario;
    private List<String> itens;

    public PedidoDto() {}

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.valorTotal = pedido.getValorTotal();
        this.dataRegistro = pedido.getDataRegistro();
        this.status = pedido.getStatus().name();
        this.usuario = pedido.getUsuario().getNome();
        this.itens = pedido.getItens().stream().map(ItemPedido::getProduto).map(Produto::getNome).collect(Collectors.toList());
    }

    public Page<PedidoDto> convert(Page<Pedido> pedidos) {
        return pedidos.map(PedidoDto::new);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public String getStatus() {
        return status;
    }

    public String getUsuario() {
        return usuario;
    }

    public List<String> getItens() {
        return itens;
    }
}
