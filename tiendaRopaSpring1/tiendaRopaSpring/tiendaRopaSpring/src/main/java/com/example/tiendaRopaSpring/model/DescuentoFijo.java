package com.example.tiendaRopaSpring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("FIJO")
@NoArgsConstructor
public class DescuentoFijo extends Descuento {

    public DescuentoFijo(Double cantidad) {
        super(cantidad);
    }

    @Override
    public Double calcularPrecioFinal(Double precioOriginal) {
        return Math.max(0, precioOriginal - this.valor);
    }
}
