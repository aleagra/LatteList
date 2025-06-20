package com.example.LatteList.DTOs.UsuarioDTOs;
import com.example.LatteList.DTOs.CafeDTOs.CafeSinResenasDTO;
import java.util.List;

public record UsuarioListaCafeDTO(
        Long id,
        String nombre,
        List<CafeSinResenasDTO> cafes
) {
}
