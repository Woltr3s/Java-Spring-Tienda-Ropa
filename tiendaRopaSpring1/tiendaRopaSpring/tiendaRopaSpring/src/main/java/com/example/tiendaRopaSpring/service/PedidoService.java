package com.example.tiendaRopaSpring.service;

import com.example.tiendaRopaSpring.dto.CarritoRequest;
import com.example.tiendaRopaSpring.model.*;
import com.example.tiendaRopaSpring.repository.PedidoRepository;
import com.example.tiendaRopaSpring.repository.ProductoRepository;
import com.example.tiendaRopaSpring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public Pedido agregarAlCarrito(CarritoRequest request) {
        List<Pedido> pedidosPendientes = pedidoRepository.findByUsuarioIdAndEstadoPedido(
                request.getUsuarioId(), ESTADO_PEDIDO.PENDIENTE);

        Pedido pedidoActual;

        if (pedidosPendientes.isEmpty()) {
            Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            pedidoActual = new Pedido();
            pedidoActual.setUsuario(usuario);
            pedidoActual.setFecha(LocalDate.now());
            pedidoActual.setEstadoPedido(ESTADO_PEDIDO.PENDIENTE);
        } else if (pedidosPendientes.size() == 1) {
            pedidoActual = pedidosPendientes.get(0);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Inconsistencia: Múltiples pedidos pendientes detectados");
        }

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        LineaPedido linea = new LineaPedido(request.getCantidad(), producto);

        pedidoActual.addLineaPedido(linea);

        return pedidoRepository.save(pedidoActual);
    }

    public Pedido finalizarPedido(Long usuarioId) {
        Pedido pedido = obtenerPedidoPendienteUnico(usuarioId);
        if (pedido.getListaLineas().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede finalizar un pedido sin productos");
        }
        pedido.setEstadoPedido(ESTADO_PEDIDO.FINALIZADO);
        pedido.setFecha(LocalDate.now());
        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido(Long usuarioId) {
        Pedido pedido = obtenerPedidoPendienteUnico(usuarioId);
        pedido.setEstadoPedido(ESTADO_PEDIDO.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido entregarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstadoPedido() != ESTADO_PEDIDO.FINALIZADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden entregar pedidos FINALIZADOS");
        }

        pedido.setEstadoPedido(ESTADO_PEDIDO.ENTREGADO);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    private Pedido obtenerPedidoPendienteUnico(Long usuarioId) {
        List<Pedido> pendientes = pedidoRepository.findByUsuarioIdAndEstadoPedido(usuarioId, ESTADO_PEDIDO.PENDIENTE);
        if (pendientes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay pedidos pendientes para este usuario");
        }
        if (pendientes.size() > 1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Múltiples pedidos pendientes encontrados");
        }
        return pendientes.getFirst();
    }
}
