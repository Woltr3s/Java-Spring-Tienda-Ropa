package com.example.tiendaRopaSpring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "descuento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_descuento", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoDescuento"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DescuentoPorcentaje.class, name = "PORCENTAJE"),
        @JsonSubTypes.Type(value = DescuentoFijo.class, name = "FIJO")
})
public abstract class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", nullable = false)
    protected Double valor;

    public Descuento(Double valor) {
        this.valor = valor;
    }

    public abstract Double calcularPrecioFinal(Double precioOriginal);
}
