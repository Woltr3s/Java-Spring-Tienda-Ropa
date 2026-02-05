package com.example.tiendaRopaSpring.dto;

import lombok.Data;

@Data
public class CarritoRequest {
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
}
