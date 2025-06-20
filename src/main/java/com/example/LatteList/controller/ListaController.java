package com.example.LatteList.controller;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListRequestDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListaCafeDTO;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.service.ListaDeCafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    ListaDeCafeService listaDeCafeService;

    public ListaController(ListaDeCafeService listaDeCafeService) {
        this.listaDeCafeService = listaDeCafeService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListaCafeDTO>> visualizarListas() {
        List<UsuarioListaCafeDTO> listasDTO = listaDeCafeService.visualizarListasDTO();
        return ResponseEntity.ok(listasDTO);
    }
    @PostMapping("/nueva")
    public ResponseEntity<Map<String, String>> agregarListaDeCafe(@RequestBody UsuarioListRequestDTO dto){
        return listaDeCafeService.agregarListaDeCafe(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> modificarNombreLista(@PathVariable Long id, @RequestBody UsuarioListRequestDTO nombre){
        return listaDeCafeService.modificarNombreLista(id, nombre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarLista(@PathVariable Long id){
        return listaDeCafeService.eliminarLista(id);
    }

    @PostMapping("/{id}/cafe/{cafeId}")
    public ResponseEntity<Map<String, String>> agregarCafeAlaLista(@PathVariable Long id, @PathVariable Long cafeId){
        return listaDeCafeService.agregarCafeALaLista(id, cafeId);
    }

    @DeleteMapping("/{listaId}/cafe/{cafeId}")
    public ResponseEntity<Map<String, String>> eliminarCafeDeLista(@PathVariable Long listaId, @PathVariable Long cafeId) {
        return listaDeCafeService.eliminarCafeDeLista(listaId, cafeId);
    }

    @GetMapping("/{id}")
    public ListaDeCafe buscarLista(@PathVariable Long id){
        return listaDeCafeService.buscarLista(id);
    }

}
