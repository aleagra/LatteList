package com.example.LatteList.DTOs.CafeDTOs;

import com.example.LatteList.Enums.CostoPromedio;

public record CafeListDTO(Long id, String nombre, String direccion, CostoPromedio costoPromedio){}