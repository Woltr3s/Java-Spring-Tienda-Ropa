package com.example.tiendaRopaSpring.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ESTADO_PEDIDO estadoPedido = ESTADO_PEDIDO.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<LineaPedido> listaLineas = new ArrayList<>();

    public void addLineaPedido(LineaPedido linea) {
        listaLineas.add(linea);
        linea.setPedido(this);
    }

    public void removeLineaPedido(LineaPedido linea) {
        listaLineas.remove(linea);
        linea.setPedido(null);
    }
}
