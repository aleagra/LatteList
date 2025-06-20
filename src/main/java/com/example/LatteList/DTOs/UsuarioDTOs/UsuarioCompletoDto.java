package com.example.LatteList.DTOs.UsuarioDTOs;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaUserDto;
import com.example.LatteList.Enums.TipoDeUsuario;

import java.util.List;

public record UsuarioCompletoDto(
        Long id,
        String nombre,
        String apellido,
        String email,
        TipoDeUsuario tipoDeUsuario,
        List<UsuarioListaCafeDTO> listasDeCafes,
        List<ResenaUserDto> resenas
    ) {}

