package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
//import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeService {

    @Autowired
    private CafeRepository repo;

    @Autowired
    private UserRepository userRepo;


    public CafeDetailDTO crearCafe(CafeRequestDTO cafeRe){
      /*  Usuario duenio = userRepo.findById(cafeRe.getIdDuenio()).
                orElseThrow(() -> DuenioNoExistenteExeption("El usuario ingresado como duenio no es valido"));*/
    Cafe cafe = new Cafe();

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


    public List<CafeListDTO> listarCafes(){
        return repo.findAll().stream().
                map(this::toListDTO)
                toList();
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

    public CafeDetailDTO modificarCafe(Long id, CafeRequestDTO datosNuevos){
        Cafe existente = repo.findById(id).
                orElseThrow(() -> new CafeNotFoundException("El cafe con id "+id+" no existe"));
       /* Usuario duenio = userRepo.findById(cafeRe.getIdDuenio()).
                orElseThrow(() -> new DuenioNoExistenteExeption("El usuario ingresado como duenio no es valido"));*/

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

    public List<CafeListDTO> buscarPorNombre(String nombre) {
    String textoLimpio = nombre.trim();
    List<Cafe> cafes = repo.findByNombreContainingIgnoreCase(textoLimpio);

     if (cafes.isEmpty()) {
       System.out.println("No se encontraron cafés con el nombre: " + nombre);
    }
    return cafes.stream()
            .map(this::toListDTO)
            .toList();
}

    public List<CafeListDTO> buscarPorDireccion(String direccion){
        String textoLimpio = direccion.trim();
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
                         .orElseThrow(() -> new BadRequestException("Etiqueta invalidad: " + etiquetaStr));

    List<Cafe> cafes = repo.findByEtiquetasContaining(etiqueta);

    return cafes.stream()
            .map(this::toListDTO)
            .toList();
}
  
public List<CafeListDTO> filtrarPorCostoPromedio(String costoStr) {
    CostoPromedio costo = validarCostoPromedio(costoStr)
            .orElseThrow(() -> new BadRequestException("Costo promedio invalido: " + costoStr));

    List<Cafe> cafes = repo.findByCostoPromedio(costo);

    if (cafes.isEmpty()) {
        System.out.println("No se encontraron cafes con ese costo promedio: " + costo);
    }

    return cafes.stream()
                .map(this::toListDTO)
                .toList();
}

    //con esto me evito usar el try-catch
    private Optional<CostoPromedio> validarCostoPromedio(String costo) {
    return Arrays.stream(CostoPromedio.values())
                 .filter(c -> c.name().equalsIgnoreCase(costo.trim()))
                 .findFirst();
}

public Optional<Etiquetas> validarEtiqueta(String etiqueta){
    return Arrays.stream(Etiquetas.values())
                .filter(c -> c.name().equalsIgnoreCase(etiqueta.trim()))
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
                cafe.getDueño().getNombre()
        );
    }

    
    private CafeListDTO toListDTO(Cafe cafe) {
    return new CafeListDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio());
    }

    
}
