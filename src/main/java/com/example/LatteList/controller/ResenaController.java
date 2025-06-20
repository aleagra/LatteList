package com.example.LatteList.controller;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaDetailDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaListDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaUserDto;
import com.example.LatteList.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResenaDetailDTO> crearResena(@Valid @RequestBody ResenaRequestDTO dto) {
        ResenaDetailDTO resenaGuardada = resenaService.postReserna(dto);
        return new ResponseEntity<>(resenaGuardada, HttpStatus.CREATED);
    }

    @GetMapping("/cafe/{idCafe}")
    public ResponseEntity<List<ResenaListDTO>> obtenerResenasDeUnCafe(@PathVariable Long idCafe) {
        List<ResenaListDTO> resenas = resenaService.getResenasPorCafe(idCafe);
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/misresenas")
    public ResponseEntity<List<ResenaUserDto>> obtenerMisResenas() {
        List<ResenaUserDto> resenas = resenaService.getResenasDelCliente();
        return ResponseEntity.ok(resenas);
    }

    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarResena(@PathVariable Long id) {
        return resenaService.deleteResena(id);
    }

}
