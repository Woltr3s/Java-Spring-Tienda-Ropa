package com.example.tiendaRopaSpring.controller;

import com.example.tiendaRopaSpring.dto.CarritoRequest;
import com.example.tiendaRopaSpring.model.Pedido;
import com.example.tiendaRopaSpring.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> agregarAlCarrito(@RequestBody CarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.agregarAlCarrito(request));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPedidos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(usuarioId));
    }

    @PutMapping("/finalizar")
    public ResponseEntity<Pedido> finalizar(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(pedidoService.finalizarPedido(usuarioId));
    }

    @PutMapping("/cancelar")
    public ResponseEntity<Pedido> cancelar(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(pedidoService.cancelarPedido(usuarioId));
    }

    @PutMapping("/entregar/{id}")
    public ResponseEntity<Pedido> entregar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.entregarPedido(id));
    }
}
