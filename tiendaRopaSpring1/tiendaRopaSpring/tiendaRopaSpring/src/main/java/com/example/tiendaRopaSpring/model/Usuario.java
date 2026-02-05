package com.example.tiendaRopaSpring.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;
    private String nombre;
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(unique = true)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Pedido> listaPedidos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Producto> favoritos = new HashSet<>();

    public Usuario(String nombre, String dni, String direccion, LocalDate fechaNacimiento, String telefono, String email, String password) {
        this.nombre = nombre;
        this.dni = dni;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    public void addFavorito(Producto producto) {
        this.favoritos.add(producto);
        producto.getUsuariosFavoritos().add(this);
    }

    public void removeFavorito(Producto producto) {
        this.favoritos.remove(producto);
        producto.getUsuariosFavoritos().remove(this);
    }
}