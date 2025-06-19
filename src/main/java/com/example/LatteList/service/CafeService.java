package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.exception.AccessDeniedException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CafeService {

    @Autowired
    private CafeRepository repo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public CafeService(CafeRepository repo, UserRepository userRepository, UserService userService) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public CafeDetailDTO crearCafe(CafeRequestDTO cafeRe){
        Usuario u = userService.getUsuarioAutenticado();

        if (!u.getTipoDeUsuario().equals(TipoDeUsuario.DUENIO)) {
            throw new AccessDeniedException("Solo los usuarios de tipo duenio pueden crear cafés.");
        }

        Cafe cafe = new Cafe();
        cafe.setNombre(cafeRe.getNombre());
        cafe.setDireccion(cafeRe.getDireccion());
        cafe.setEtiquetas(cafeRe.getEtiquetas());
        cafe.setCostoPromedio(cafeRe.getCostoPromedio());
        cafe.setLogo(cafeRe.getLogo());
        cafe.setInstagramURL(cafeRe.getInstagramURL());
        cafe.setDuenio(u);
        repo.save(cafe);

    return toDetailDTO(cafe);
    }

    @Transactional
    public CafeDetailDTO modificarCafe(Long id, CafeRequestDTO datosNuevos) {
        Cafe existente = buscarPorIdAux(id);
        Usuario u = userService.getUsuarioAutenticado();

        if (!existente.getDuenio().getId().equals(u.getId())) {
            throw new AccessDeniedException("No puedes modificar un café que no te pertenece.");
        }

        existente.setInstagramURL(datosNuevos.getInstagramURL());
        existente.setDireccion(datosNuevos.getDireccion());
        existente.setLogo(datosNuevos.getLogo());
        existente.setEtiquetas(datosNuevos.getEtiquetas());
        existente.setCostoPromedio(datosNuevos.getCostoPromedio());
        existente.setNombre(datosNuevos.getNombre());

        repo.save(existente);
        return toDetailDTO(existente);
    }

    public CafeDetailDTO buscarPorId(Long id){
        Cafe cafe = buscarPorIdAux(id);
        return toDetailDTO(cafe);
    }

    @Transactional
    public void eliminarCafe(Long id) {
        Cafe cafe = buscarPorIdAux(id);
        Usuario u = userService.getUsuarioAutenticado();
        if (u.getTipoDeUsuario() != TipoDeUsuario.ADMIN &&
                !cafe.getDuenio().getId().equals(u.getId())) {
            throw new AccessDeniedException("No tenes permiso para eliminar este cafe");
        }
        repo.deleteById(id);
    }

    //------------------------FILTROS Y DEMAS--------------------------------------------------------------------------------------

    public List<CafeListDTO> filtrarPorDuenio(String nombre, String apellido){
        Usuario u = userService.getUsuarioAutenticado();
        String nombreLimpio = nombre.trim();
        String apellidoLimpio = apellido.trim();
        List<Usuario> usuarios = userRepository.findByNombreAndApellido(nombre, apellido);

        if(usuarios.isEmpty()){
            throw new EntityNotFoundException("No existe ningun usuario de nombre:"+nombre+" "+apellido);
        }

        List<Cafe> cafes = repo.findByDuenioIn(usuarios);

        if (cafes.isEmpty()) {
            System.out.println("No se encontraron cafes del usuario/s con nombre: " +nombre+" "+apellido);
        }

        return cafes.stream()
                .map(this::toListDTO)
                .toList();
    }


    public List<CafeListDTO> filtrarPorNombre(String nombre) {
    String textoLimpio = nombre.trim();
    List<Cafe> cafes = repo.findByNombreContainingIgnoreCase(textoLimpio);

     if (cafes.isEmpty()) {
       System.out.println("No se encontraron cafes con el nombre: " + nombre);
    }
    return cafes.stream()
            .map(this::toListDTO)
            .toList();
}

    public List<CafeListDTO> filtrarPorDireccionAprox(String direccionParcial){
        String textoLimpio = direccionParcial.trim();
        List<Cafe> cafes = repo.findByDireccionContainingIgnoreCase(textoLimpio);
    
        if (cafes.isEmpty()) {
       System.out.println("No se encontraron cafés con la direccion: " + direccionParcial);
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

    public Cafe buscarPorIdAux(Long id){
        Cafe cafe = repo.findById(id).
                orElseThrow(() -> new CafeNotFoundException("El cafe con id "+id+" no existe"));

        return cafe;
    }

private Optional<Etiquetas> validarEtiqueta(String etiqueta){
    return Arrays.stream(Etiquetas.values())
                .filter(c -> c.name().equalsIgnoreCase(etiqueta.trim()))
                 .findFirst();
}

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
                cafe.getDuenio().getNombre(),
                cafe.getDuenio().getApellido(),
                cafe.getDuenio().getId()

        );
    }

    private CafeListDTO toListDTO(Cafe cafe) {
        return new CafeListDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio(), cafe.getDuenio().getId());
    }

}
