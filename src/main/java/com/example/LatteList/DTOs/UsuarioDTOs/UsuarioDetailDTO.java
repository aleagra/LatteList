package com.example.LatteList.DTOs.UsuarioDTOs;

import com.example.LatteList.Enums.TipoDeUsuario;

public record UsuarioDetailDTO(
        Long id,
        String nombre,
        String apellido,
        String email,
        TipoDeUsuario tipoDeUsuario
){}
