package com.example.tiendaRopaSpring.repository;

import com.example.tiendaRopaSpring.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p JOIN p.usuariosFavoritos u WHERE u.id = :usuarioId")
    List<Producto> findFavoritosByUsuarioId(@Param("usuarioId") Long usuarioId);
}
