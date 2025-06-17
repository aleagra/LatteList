package com.example.LatteList.controller;
import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.service.CafeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    @Autowired
    private CafeService service;

   @PreAuthorize("hasRole('ADMIN') or hasRole('DUENIO')")
    @PostMapping
    public ResponseEntity<CafeDetailDTO> createCafe(@RequestBody @Valid CafeRequestDTO cafe){
        CafeDetailDTO cafeCreado = service.crearCafe(cafe);
        return ResponseEntity.ok(cafeCreado);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DUENIO')")
    @PutMapping("id/{id}")
    public ResponseEntity<CafeDetailDTO> modificarCafe(@RequestBody @Valid CafeRequestDTO datosNuevos, @PathVariable Long id){
        CafeDetailDTO cafe = service.modificarCafe(id, datosNuevos);
        return ResponseEntity.ok(cafe);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DUENIO')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id){
        service.eliminarCafe(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("id/{id}")
    public ResponseEntity<CafeDetailDTO> getById(@PathVariable Long id){
        CafeDetailDTO cafe = service.buscarPorId(id);
        return ResponseEntity.ok(cafe);
    }

    @GetMapping
    public ResponseEntity<List<CafeListDTO>> getAll(){
        List<CafeListDTO> cafes = service.listarCafes();

        if(cafes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cafes);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DUENIO')")
    @GetMapping("/duenio")
    public ResponseEntity<List<CafeListDTO>> getCafesPorDuenio(@RequestParam Long idDuenio) {
        List<CafeListDTO> cafes = service.filtrarPorDuenio(idDuenio);

        if (cafes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cafes);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/aleatorio")
    public ResponseEntity<CafeDetailDTO> getRandomCafe() {
        CafeDetailDTO cafe = service.obtenerCafeAleatorio();
        return ResponseEntity.ok(cafe);
    }

    @GetMapping("/costoPromedio")
    public ResponseEntity<List<CafeListDTO>> getAllByCostoPromedio(@RequestParam String costoPromedio){
        List<CafeListDTO> cafes = service.filtrarPorCostoPromedio(costoPromedio);

        if(cafes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cafes);
      }


    @GetMapping("/etiquetas")
    public ResponseEntity<List<CafeListDTO>> getAllByEtiquetas(@RequestParam String etiqueta){
        List<CafeListDTO> cafes = service.filtrarPorEtiqueta(etiqueta);

        if(cafes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cafes);
    }

   @GetMapping("/direccion")
   public ResponseEntity<List<CafeListDTO>> getAllByDireccion(@RequestParam String direccion){
       List<CafeListDTO> cafes = service.filtrarPorDireccionAprox(direccion);

       if(cafes.isEmpty()){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(cafes);
   }

    @GetMapping("/nombre")
    public ResponseEntity<List<CafeListDTO>> getAllByName(@RequestParam String nombre){
        List<CafeListDTO> cafes = service.filtrarPorNombre(nombre);

        if(cafes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cafes);
    }



}
