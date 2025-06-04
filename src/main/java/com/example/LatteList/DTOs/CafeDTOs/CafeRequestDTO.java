package com.example.LatteList.DTOs.CafeDTOs;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class CafeRequestDTO {

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
    private Set<Etiquetas> etiquetas = new HashSet<Etiquetas>();

    @NotNull(message = "El ID del dueño es obligatorio.")
    private Long idDuenio;

    public CafeRequestDTO(String nombre, String direccion,
                          CostoPromedio costoPromedio, String logo, String instagramURL, Set<Etiquetas> etiquetas, Long idDuenio) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.costoPromedio = costoPromedio;
        this.logo = logo;
        this.instagramURL = instagramURL;
        this.etiquetas = etiquetas;
        this.idDuenio = idDuenio;
    }

    public CafeRequestDTO(String nombre, String direccion, CostoPromedio costoPromedio, Set<Etiquetas> etiquetas, Long idDuenio) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.costoPromedio = costoPromedio;
        this.etiquetas = etiquetas;
        this.idDuenio = idDuenio;
    }

    public CafeRequestDTO() {
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

    public Long getIdDuenio() {
        return idDuenio;
    }

}





