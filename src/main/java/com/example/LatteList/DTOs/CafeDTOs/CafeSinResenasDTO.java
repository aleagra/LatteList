package com.example.LatteList.DTOs.CafeDTOs;
import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import java.util.Set;

public record CafeSinResenasDTO(
        Long id,
        String nombre,
        String direccion,
        CostoPromedio costoPromedio,
        String logo,
        String instagramURL,
        Set<Etiquetas> etiquetas
) {}