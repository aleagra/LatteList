package com.example.LatteList.DTOs.ResenaDTOs;

import java.time.LocalDate;

public record ResenaUserDto(
        Long id,
        String nombrecafe,
        String direccion,
        Integer puntuacionGeneral,
        Integer puntuacionPrecio,
        Integer puntuacionAtencion,
        String comentario,
        LocalDate fecha
) {}
