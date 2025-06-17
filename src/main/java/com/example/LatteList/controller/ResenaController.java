package com.example.LatteList.controller;

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
    @Autowired
    private ResenaService resenaService;

    //solo clinetes suben resenas.
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<Resena> crearResena(@Valid @RequestBody ResenaRequestDTO dto) {
        Resena nuevaResena = resenaService.postReserna(dto,nuevaResena.getUsuario().getEmail());
        return new ResponseEntity<>(nuevaResena, HttpStatus.CREATED);
    }

    //todos loos usuarios registrados. pueden ver todas las resenas de un cafe .
    @PreAuthorize("hasRole('CLIENTE') or hasRole('DUENIO') or hasRole('ADMIN')")
    @GetMapping("/cafe/{idCafe}/resenas")
    public ResponseEntity<?> obtenerResenasDeUnCafe(@PathVariable Long idCafe) {
        List<Resena> resenas = resenaService.getResenasPorCafe(idCafe);

        if (resenas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Este café aún no tiene reseñas.");
        }

        return ResponseEntity.ok(resenas);
    }

    //el cliente puede ver sus resenas !
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/misresenas")
    public ResponseEntity<List<Resena>> obtenerMisResenas() {
        // Obtener el nombre de usuario autenticado
        String usernameActual = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar las reseñas del cliente autenticado
        List<Resena> resenas = resenaService.getResenasDelCliente(usernameActual);

        return ResponseEntity.ok(resenas);
    }

 //borrar resenas. clientes las suyas. admin todas.
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarResena(@PathVariable Long id) {
        //guardo para ver que rol ingreso.
        String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

        //ver si es admin o cliente.
        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMIN"));

        Resena resena = resenaService.getResenaById(id); //ve si existe.

        // Si no es admin, validar que la reseña sea del usuario
        if (!esAdmin && !resena.getCliente().getEmail().equals(usuarioActual)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes eliminar esta reseña, no es tuya");
        }
       //si es admin borra cualquirea.
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }

}
