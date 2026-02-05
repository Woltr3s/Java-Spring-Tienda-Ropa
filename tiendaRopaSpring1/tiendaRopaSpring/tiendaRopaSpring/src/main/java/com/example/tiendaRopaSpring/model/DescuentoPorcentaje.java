package com.example.tiendaRopaSpring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PORCENTAJE")
@NoArgsConstructor
public class DescuentoPorcentaje extends Descuento {

    public DescuentoPorcentaje(Double porcentaje) {
        super(porcentaje);
    }

    @Override
    public Double calcularPrecioFinal(Double precioOriginal) {
        return precioOriginal - (precioOriginal * (this.valor / 100));
    }
}
