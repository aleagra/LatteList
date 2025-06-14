package com.example.LatteList.DTOs.ListaCafeDTOs;

import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;

import java.util.List;

public record ListaDeCafeDetailDTO(Long id, String nombre, List<CafeDetailDTO> cafe) {
}
