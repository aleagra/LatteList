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
            regexp = "^[\\p{L}0-9\\.]+(?:\\s[\\p{L}0-9\\.]+)*\\s\\d+$",
            message = "La dirección debe tener el formato: Calle seguido de la altura numérica, por ejemplo 'San Martín 2475' o 'Av. 9 de Julio 123'."
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
    private Set<String> etiquetas = new HashSet<String>();

    public @NotBlank(message = "El nombre es obligatorio.") @Size(min = 3, max = 20, message = "El nombre debe contener entre 3 a 20 caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio.") @Size(min = 3, max = 20, message = "El nombre debe contener entre 3 a 20 caracteres") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "La direccion es obligatoria.") @Size(min = 3, max = 30, message = "La direccion debe contener entre 3 a 30 caracteres") @Pattern(
            regexp = "^[A-ZÁÉÍÓÚÑa-záéíóúñ]+\\s\\d+$",
            message = "La dirección debe tener el formato: Calle seguido de la altura numérica, por ejemplo 'Lavalle 1234'."
    ) String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotBlank(message = "La direccion es obligatoria.") @Size(min = 3, max = 30, message = "La direccion debe contener entre 3 a 30 caracteres") @Pattern(
            regexp = "^[A-ZÁÉÍÓÚÑa-záéíóúñ]+\\s\\d+$",
            message = "La dirección debe tener el formato: Calle seguido de la altura numérica, por ejemplo 'Lavalle 1234'."
    ) String direccion) {
        this.direccion = direccion;
    }

    public @NotNull(message = "El promedio es obligatorio") CostoPromedio getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(@NotNull(message = "El promedio es obligatorio") CostoPromedio costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public @Pattern(
            regexp = "^(https?://).+\\.(png|jpg|jpeg|svg|webp)$",
            message = "El logo debe ser una URL válida a una imagen (.png, .jpg, etc.)"
    ) String getLogo() {
        return logo;
    }

    public void setLogo(@Pattern(
            regexp = "^(https?://).+\\.(png|jpg|jpeg|svg|webp)$",
            message = "El logo debe ser una URL válida a una imagen (.png, .jpg, etc.)"
    ) String logo) {
        this.logo = logo;
    }

    public @Pattern(
            regexp = "^(https?://)?(www\\.)?instagram\\.com/[A-Za-z0-9_.]+/?$",
            message = "Debe ser un enlace válido de Instagram."
    ) String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(@Pattern(
            regexp = "^(https?://)?(www\\.)?instagram\\.com/[A-Za-z0-9_.]+/?$",
            message = "Debe ser un enlace válido de Instagram."
    ) String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public @Size(min = 1, message = "Debe tener al menos una etiqueta.") Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(@Size(min = 1, message = "Debe tener al menos una etiqueta.") Set<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
