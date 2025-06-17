package com.example.LatteList.DTOs.UsuarioDTOs;

import com.example.LatteList.model.ListaDeCafe;

import java.util.List;

public record UsuarioListaDeCafeDTO(
        List<ListaDeCafe> listaDeCafes
) {}
