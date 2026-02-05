package com.example.tiendaRopaSpring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("PANTALON")
public class Pantalon extends Producto {

    private int bolsillos;

    public Pantalon(String nombre, String marca, Double precio, TALLA talla, COLOR color, Etiqueta etiqueta, Descuento descuento, int bolsillos) {
        super(nombre, marca, precio, talla, color, etiqueta, descuento);
        this.bolsillos = bolsillos;
    }
}
