package com.example.LatteList.model;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(name = "cafes")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CostoPromedio costoPromedio;

    private String logo;

    private String instagramURL;

    @ElementCollection(targetClass = Etiquetas.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "cafe_etiquetas",
            joinColumns = @JoinColumn(name = "cafe_id")
    )
    @Column(name = "etiqueta")
    private Set<Etiquetas> etiquetas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "dueño_id", nullable = false)
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario duenio;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Resena> resenas = new ArrayList<>();

    public Cafe() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CostoPromedio getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(CostoPromedio costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public Set<Etiquetas> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiquetas> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Usuario getDuenio() {
        return duenio;
    }

    public void setDuenio(Usuario duenio) {
        this.duenio = duenio;
    }

    public List<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }
}