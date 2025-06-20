package com.example.LatteList.controller;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaDetailDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaListDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
import com.example.LatteList.model.Resena;
import com.example.LatteList.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/resenas")
public class ResenaController {
    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }
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


    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/misresenas")
    public ResponseEntity<List<ResenaListDTO>> obtenerMisResenas() {
        List<ResenaListDTO> resenas = resenaService.getResenasDelCliente();
        return ResponseEntity.ok(resenas);
    }

    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }

}
