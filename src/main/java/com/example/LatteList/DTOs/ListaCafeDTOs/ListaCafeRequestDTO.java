package com.example.LatteList.DTOs.ListaCafeDTOs;

import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;

import java.util.List;

//No se pasa el id del usuario porque en teoria cuandos se llame a este dto, el usuario ya deberia
//estar verificado
public record ListaCafeRequestDTO(String nombre,  List<Long> idCafes) {
}
