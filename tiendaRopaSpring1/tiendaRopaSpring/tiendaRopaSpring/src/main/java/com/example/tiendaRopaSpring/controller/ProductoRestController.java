package com.example.tiendaRopaSpring.controller;

import com.example.tiendaRopaSpring.dto.FavoritoRequest;
import com.example.tiendaRopaSpring.model.Producto;
import com.example.tiendaRopaSpring.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(producto));
    }

    @PutMapping
    public ResponseEntity<Producto> actualizarProductoCuerpo(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favoritos/{usuarioId}")
    public ResponseEntity<List<Producto>> obtenerFavoritos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(productoService.obtenerFavoritos(usuarioId));
    }

    @PostMapping("/favoritos")
    public ResponseEntity<String> anadirFavorito(@RequestBody FavoritoRequest request) {
        productoService.agregarFavorito(request.getUsuarioId(), request.getProductoId());
        return ResponseEntity.ok("Producto añadido a favoritos correctamente");
    }
}
