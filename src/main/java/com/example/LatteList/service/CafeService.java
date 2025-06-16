package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
//import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CafeService {

    @Autowired
    private CafeRepository repo;

    @Autowired
    private UserRepository userRepo;


    @Transactional
    public CafeDetailDTO crearCafe(CafeRequestDTO cafeRe){
      /*  Usuario duenio = userRepo.findById(cafeRe.getIdDuenio()).
                orElseThrow(() -> DuenioNoExistenteExeption("El usuario ingresado como duenio no es valido"));*/
    Cafe cafe = new Cafe();


    // falta verificar que sea el duenio
        
    cafe.setNombre(cafeRe.getNombre());
    cafe.setDireccion(cafeRe.getDireccion());
   // cafe.setDueño(duenio);
    cafe.setEtiquetas(cafeRe.getEtiquetas());
    cafe.setCostoPromedio(cafeRe.getCostoPromedio());
    cafe.setLogo(cafeRe.getLogo());
    cafe.setInstagramURL(cafeRe.getInstagramURL());

    repo.save(cafe);

    return toDetailDTO(cafe);
    }


    @Transactional
    public CafeDetailDTO modificarCafe(Long id, CafeRequestDTO datosNuevos){
        Cafe existente = repo.findById(id).
                orElseThrow(() -> new CafeNotFoundException("El cafe con id "+id+" no existe"));
       /* Usuario duenio = userRepo.findById(cafeRe.getIdDuenio()).
                orElseThrow(() -> new DuenioNoExistenteExeption("El usuario ingresado como duenio no es valido"));*/

        //falta verificar usuario duenio y admin

        existente.setInstagramURL(datosNuevos.getInstagramURL());
        existente.setDireccion(datosNuevos.getDireccion());
        existente.setLogo(datosNuevos.getLogo());
        existente.setEtiquetas(datosNuevos.getEtiquetas());
        //existente.setDueño(duenio);
        existente.setCostoPromedio(datosNuevos.getCostoPromedio());
        existente.setNombre(datosNuevos.getNombre());

        repo.save(existente);

        return toDetailDTO(existente);
    }

    //La excepcion esta en la rama de ceci
    public CafeDetailDTO buscarPorId(Long id){
        Cafe cafe = repo.findById(id).
                orElseThrow(() -> new CafeNotFoundException("El cafe con id "+id+" no existe"));

        return toDetailDTO(cafe);
    }


    public void eliminarCafe(Long id){

        if(!repo.existsById(id)){
            throw new CafeNotFoundException("El cafe con id "+id+" no existe");
        }
        repo.deleteById(id);
    }


    //------------------------FILTROS Y DEMAS--------------------------------------------------------------------------------------

    public List<CafeListDTO> filtrarPorNombre(String nombre) {
    String textoLimpio = nombre.trim();
    List<Cafe> cafes = repo.findByNombreContainingIgnoreCase(textoLimpio);

     if (cafes.isEmpty()) {
       System.out.println("No se encontraron cafés con el nombre: " + nombre);
    }
    return cafes.stream()
            .map(this::toListDTO)
            .toList();
}

    public List<CafeListDTO> filtrarPorDireccionAprox(String direccionParcial){
        String textoLimpio = direccionParcial.trim();
        List<Cafe> cafes = repo.findByDireccionContainingIgnoreCase(textoLimpio);
    
        if (cafes.isEmpty()) {
       System.out.println("No se encontraron cafés con la direccion: " + direccion);
        }
           return cafes.stream()
            .map(this::toListDTO)
            .toList();
    }

    public List<CafeListDTO> filtrarPorEtiqueta(String etiquetaStr) {
    Etiquetas etiqueta = validarEtiqueta(etiquetaStr)
                         .orElseThrow(() -> new CafeNotFoundException("Etiqueta invalidad: " + etiquetaStr));

    List<Cafe> cafes = repo.findByEtiquetasContaining(etiqueta);

    return cafes.stream()
            .map(this::toListDTO)
            .toList();
}
  
public List<CafeListDTO> filtrarPorCostoPromedio(String costoStr) {
    CostoPromedio costo = validarCostoPromedio(costoStr).
            orElseThrow(() -> new CafeNotFoundException("Costo promedio invalido: " + costoStr));

    List<Cafe> cafes = repo.findByCostoPromedio(costo);

    if (cafes.isEmpty()) {
        System.out.println("No se encontraron cafes con ese costo promedio: " + costo);
    }

    return cafes.stream()
                .map(this::toListDTO)
                .toList();
}

    public List<CafeListDTO> listarCafes(){
        return repo.findAll().stream().
                map(this::toListDTO).
        toList();
    }

    public CafeDetailDTO obtenerCafeAleatorio() {
        Cafe cafe = repo.obtenerCafeAleatorio().
                orElseThrow(() -> new CafeNotFoundException("No hay cafes en la base de datos."));
        return toDetailDTO(cafe);
    }


//------------------------AUXILIARES--------------------------------------------------------------------------------------


private Optional<Etiquetas> validarEtiqueta(String etiqueta){
    return Arrays.stream(Etiquetas.values())
                .filter(c -> c.name().equalsIgnoreCase(etiqueta.trim()))
                 .findFirst();
}

    //con esto me evito usar el try-catch
    private Optional<CostoPromedio> validarCostoPromedio(String costo) {
        return Arrays.stream(CostoPromedio.values())
                .filter(c -> c.name().equalsIgnoreCase(costo.trim()))
                .findFirst();
    }



    private CafeDetailDTO toDetailDTO(Cafe cafe) {
        return new CafeDetailDTO(
                cafe.getId(),
                cafe.getNombre(),
                cafe.getDireccion(),
                cafe.getCostoPromedio(),
                cafe.getLogo(),
                cafe.getInstagramURL(),
                cafe.getEtiquetas(),
                cafe.getDuenio().getNombre()
        );
    }



    private CafeListDTO toListDTO(Cafe cafe) {
        return new CafeListDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio());
    }




}
