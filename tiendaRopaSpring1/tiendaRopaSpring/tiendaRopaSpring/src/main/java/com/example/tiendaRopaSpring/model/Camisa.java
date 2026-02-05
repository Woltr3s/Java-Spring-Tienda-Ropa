package com.example.tiendaRopaSpring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("CAMISA")
public class Camisa extends Producto {

    private int botones;

    public Camisa(String nombre, String marca, Double precio, TALLA talla, COLOR color, Etiqueta etiqueta, Descuento descuento, int botones) {
        super(nombre, marca, precio, talla, color, etiqueta, descuento);
        this.botones = botones;
    }
}
