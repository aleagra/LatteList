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

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 3, max = 20, message = "El nombre debe contener entre 3 a 20 caracteres")
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria.")
    @Size(min = 3, max = 30, message = "La direccion debe contener entre 3 a 30 caracteres")
    @Pattern(
            regexp = "^[A-ZÁÉÍÓÚÑa-záéíóúñ]+\\s\\d+$",
            message = "La dirección debe tener el formato: Calle seguido de la altura numérica, por ejemplo 'Lavalle 1234'."
    )
    private String direccion;

    @NotNull(message = "El promedio es obligatorio")
    private CostoPromedio costoPromedio;


    @Pattern(
            regexp = "^(https?://).+\\.(png|jpg|jpeg|svg|webp)$",
            message = "El logo debe ser una URL válida a una imagen (.png, .jpg, etc.)"
    )
    private String logo;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?instagram\\.com/[A-Za-z0-9_.]+/?$",
            message = "Debe ser un enlace válido de Instagram."
    )
    private String instagramURL;

    @Size(min = 1, message = "Debe tener al menos una etiqueta.")
    //Esto asi no se guarda en la base de datos
    private Set<Etiquetas> etiquetas = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "dueño_id", nullable = false)
    private Usuario dueño;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resena> reseñas = new ArrayList<>();


    public Cafe(Long id, String nombre, String direccion, CostoPromedio costoPromedio, String logo, String instagramURL, Usuario dueño) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.costoPromedio = costoPromedio;
        this.logo = logo;
        this.instagramURL = instagramURL;
        this.dueño = dueño;
    }



    public Cafe() {
    }

    @Override
    public String toString() {
        return
                "| Id: " +id  + "\n" +
                        "| Nombre del cafe: " + nombre + "\n" +
                        "| Direcccion: " + direccion + "\n" +
                        "| Costo promedio: " + costoPromedio  + "\n" +
                        //         "| Dueño : " + dueño.getNombre +" "+dueño.getApellido+ "\n" +
                        "=========================================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cafe cafe = (Cafe) o;
        return Objects.equals(id, cafe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.trim() ;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion =  direccion.trim();
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