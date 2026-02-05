package com.example.tiendaRopaSpring.dto;

import lombok.Data;

@Data
public class FavoritoRequest {
    private Long usuarioId;
    private Long productoId;
}
