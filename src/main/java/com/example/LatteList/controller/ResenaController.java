package com.example.LatteList.controller;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
import com.example.LatteList.model.Resena;
import com.example.LatteList.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public ResponseEntity<Resena> crearResena(@Valid @RequestBody ResenaRequestDTO dto) {
        Resena nuevaResena = resenaService.postReserna(dto);
        return new ResponseEntity<>(nuevaResena, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasResenas() {
        List<Resena> resenas = resenaService.getAllResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay reseñas registradas.");
        }
        return ResponseEntity.ok(resenas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
        Resena resena = resenaService.getResenaById(id);
        return ResponseEntity.ok(resena);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @Valid @RequestBody ResenaRequestDTO dto) {
        Resena resenaActualizada = resenaService.actualizarResena(id, dto);
        return ResponseEntity.ok(resenaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build(); // HTTP 204 sin contenido
    }

}
