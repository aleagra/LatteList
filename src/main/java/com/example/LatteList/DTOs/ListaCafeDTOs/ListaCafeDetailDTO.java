package com.example.LatteList.DTOs.ListaCafeDTOs;

import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;

import java.util.List;

public record ListaCafeDetailDTO(Long id, String nombre, List<CafeListDTO> cafe) {
}
