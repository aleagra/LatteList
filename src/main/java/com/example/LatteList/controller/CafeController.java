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
import java.util.Map;

@RestController
@RequestMapping("/cafes")
public class CafeController {


    @Autowired
    private CafeService service;


    @PreAuthorize("hasRole('DUENIO')")
    @PostMapping
    public ResponseEntity<CafeDetailDTO> createCafe(@RequestBody @Valid CafeRequestDTO cafe){
        CafeDetailDTO cafeCreado = service.crearCafe(cafe);
        return ResponseEntity.ok(cafeCreado);
    }


    @PreAuthorize("hasRole('DUENIO')")
    @PutMapping("/{id}")
    public ResponseEntity<CafeDetailDTO> modificarCafe(@RequestBody @Valid CafeRequestDTO datosNuevos, @PathVariable Long id){
        CafeDetailDTO cafe = service.modificarCafe(id, datosNuevos);
        return ResponseEntity.ok(cafe);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('DUENIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCafe(@PathVariable Long id) {
        return service.eliminarCafe(id);
    }



    @GetMapping("/{id}")
    public ResponseEntity<CafeDetailDTO> getById(@PathVariable Long id){
        CafeDetailDTO cafe = service.buscarPorId(id);
        return ResponseEntity.ok(cafe);
    }


    @GetMapping
    public ResponseEntity<List<CafeListDTO>> getAll(){
        List<CafeListDTO> cafes = service.listarCafes();
        return ResponseEntity.ok(cafes);
    }


       @GetMapping("/duenio/{nombre}/{apellido}")
    public ResponseEntity<List<CafeListDTO>> getCafesPorDuenio(@PathVariable String nombre, @PathVariable String apellido) {

        List<CafeListDTO> cafes = service.filtrarPorDuenio(nombre, apellido);
        return ResponseEntity.ok(cafes);
    }


    @GetMapping("/aleatorio")
    public ResponseEntity<CafeDetailDTO> getRandomCafe() {
        CafeDetailDTO cafe = service.obtenerCafeAleatorio();
        return ResponseEntity.ok(cafe);
    }


    @GetMapping("/costo/{costoPromedio}")
    public ResponseEntity<List<CafeListDTO>> getAllByCostoPromedio(@PathVariable String costoPromedio){
        List<CafeListDTO> cafes = service.filtrarPorCostoPromedio(costoPromedio);
        return ResponseEntity.ok(cafes);
      }



    @GetMapping("/etiqueta/{etiqueta}")
    public ResponseEntity<List<CafeListDTO>> getAllByEtiquetas(@PathVariable String etiqueta){
        List<CafeListDTO> cafes = service.filtrarPorEtiqueta(etiqueta);
        return ResponseEntity.ok(cafes);
    }

    @GetMapping("/etiquetas")
    public ResponseEntity<List<String>> obtenerEtiquetasPosibles() {
        List<String> etiquetas = service.verTodasLasEtiquetas();
        return ResponseEntity.ok(etiquetas);
    }




   @GetMapping("/direccion/{direccion}")
   public ResponseEntity<?> getAllByDireccion(@PathVariable  String direccion){
       List<CafeListDTO> cafes = service.filtrarPorDireccionAprox(direccion);

       if(cafes.isEmpty()){
          return ResponseEntity.ok("No existe ningun cafe ubicado en "+direccion+", por favor verifique estar colocando bien la direccion o ingrese solo la calle en caso de que este mal el numeral.");
       }

       return ResponseEntity.ok(cafes);
   }


    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getAllByName(@PathVariable  String nombre){
        List<CafeListDTO> cafes = service.filtrarPorNombre(nombre);

        if(cafes.isEmpty()){
            return ResponseEntity.ok("No existe ningun cafe con el nombre '"+nombre+"' verifique estar colocandolo correctamente.");
        }
        return ResponseEntity.ok(cafes);
    }

}
