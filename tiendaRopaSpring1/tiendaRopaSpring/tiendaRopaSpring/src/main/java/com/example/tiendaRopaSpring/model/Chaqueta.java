package com.example.tiendaRopaSpring.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("CHAQUETA")
public class Chaqueta extends Producto {

    @Column(name = "con_capucha")
    private boolean conCapucha;

    @Column(name = "nivel_abrigo")
    private int nivelAbrigo;

    public Chaqueta(String nombre, String marca, Double precio, TALLA talla, COLOR color, Etiqueta etiqueta, Descuento descuento, boolean conCapucha, int nivelAbrigo) {
        super(nombre, marca, precio, talla, color, etiqueta, descuento);
        this.conCapucha = conCapucha;
        this.nivelAbrigo = nivelAbrigo;
    }
}
