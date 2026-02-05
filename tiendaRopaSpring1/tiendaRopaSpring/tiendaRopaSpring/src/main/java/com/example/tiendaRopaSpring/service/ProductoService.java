package com.example.tiendaRopaSpring.service;

import com.example.tiendaRopaSpring.model.Producto;
import com.example.tiendaRopaSpring.model.Usuario;
import com.example.tiendaRopaSpring.repository.ProductoRepository;
import com.example.tiendaRopaSpring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto guardarProducto(Producto producto) {
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no puede ser nulo");
        }
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Producto producto) {
        if (producto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID es obligatorio dentro del JSON para actualizar");
        }
        if (!productoRepository.existsById(producto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + producto.getId());
        }
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID no puede ser nulo");
        }
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public List<Producto> obtenerFavoritos(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return productoRepository.findFavoritosByUsuarioId(usuarioId);
    }

    public void agregarFavorito(Long usuarioId, Long productoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Producto producto = buscarPorId(productoId);

        if (usuario.getFavoritos().contains(producto)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El producto ya está en favoritos");
        }

        usuario.getFavoritos().add(producto);
        usuarioRepository.save(usuario);
    }
}
