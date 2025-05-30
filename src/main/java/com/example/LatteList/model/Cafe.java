package com.example.LatteList.model;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "cafe")
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

    //ESTO NO SE GUARDA EN LA BDD
    private Set<Etiquetas> etiquetas = new HashSet<Etiquetas>();


    @ManyToOne
    @JoinColumn(name = "dueño_id", nullable = false)
    private Usuario dueño;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resena> reseñas = new ArrayList<>();




    //
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

    public Usuario getDueño() {
        return dueño;
    }

    public void setDueño(Usuario dueño) {
        this.dueño = dueño;
    }

    public List<Resena> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Resena> reseñas) {
        this.reseñas = reseñas;
    }
}