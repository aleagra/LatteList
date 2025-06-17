package com.example.LatteList.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listas")
public class ListaDeCafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "lista_cafe",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "cafe_id")
    )
    private List<Cafe> cafes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Cafe> getCafes() {
        return cafes;
    }

    public void setCafes(List<Cafe> cafes) {
        this.cafes = cafes;
    }

    public void cargarUnCafe(Cafe cafe) {
        if (cafe != null && !cafes.contains(cafe)) {
            cafes.add(cafe);
        }
    }


}
