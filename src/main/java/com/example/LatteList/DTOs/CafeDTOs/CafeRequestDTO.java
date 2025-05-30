package com.example.LatteList.DTOs.CafeDTOs;

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

}
