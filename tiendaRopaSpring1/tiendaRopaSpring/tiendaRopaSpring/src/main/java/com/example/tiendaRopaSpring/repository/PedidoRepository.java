package com.example.tiendaRopaSpring.repository;

import com.example.tiendaRopaSpring.model.ESTADO_PEDIDO;
import com.example.tiendaRopaSpring.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioIdAndEstadoPedido(Long usuarioId, ESTADO_PEDIDO estadoPedido);

    List<Pedido> findByUsuarioId(Long usuarioId);
}
