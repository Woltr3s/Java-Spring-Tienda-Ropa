package com.example.tiendaRopaSpring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoProducto"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Camisa.class, name = "CAMISA"),
        @JsonSubTypes.Type(value = Pantalon.class, name = "PANTALON"),
        @JsonSubTypes.Type(value = Chaqueta.class, name = "CHAQUETA")
})
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String marca;

    @Column(name = "precio_inicial", nullable = false)
    private Double precioInicial;

    @Enumerated(EnumType.STRING)
    private TALLA talla;

    @Enumerated(EnumType.STRING)
    private COLOR color;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "etiqueta_id")
    private Etiqueta etiqueta;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "descuento_id")
    private Descuento descuento;

    @ManyToMany(mappedBy = "favoritos", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    public Producto(String nombre, String marca, Double precioInicial, TALLA talla, COLOR color, Etiqueta etiqueta, Descuento descuento) {
        this.nombre = nombre;
        this.marca = marca;
        this.precioInicial = precioInicial;
        this.talla = talla;
        this.color = color;
        this.etiqueta = etiqueta;
        this.descuento = descuento;
    }

    public Double getPrecioFinal() {
        if (descuento != null) {
            return descuento.calcularPrecioFinal(precioInicial);
        }
        return precioInicial;
    }
}
