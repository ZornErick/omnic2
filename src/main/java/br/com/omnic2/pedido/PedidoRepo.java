package br.com.omnic2.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepo extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByUsuario_Id(Long id, Pageable pageable);
}
