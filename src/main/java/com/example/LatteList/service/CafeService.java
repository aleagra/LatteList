package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeDetailDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.exception.DuenioNoExistenteExeption;
import com.example.LatteList.exception.AccessDeniedException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    public CafeDetailDTO crearCafe(CafeRequestDTO cafeRe){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario actual = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Cafe cafe = new Cafe();
        cafe.setNombre(cafeRe.getNombre());
        cafe.setDireccion(cafeRe.getDireccion());
        cafe.setEtiquetas(cafeRe.getEtiquetas());
        cafe.setCostoPromedio(cafeRe.getCostoPromedio());
        cafe.setLogo(cafeRe.getLogo());
        cafe.setInstagramURL(cafeRe.getInstagramURL());

        if (actual.getTipoDeUsuario() == TipoDeUsuario.ADMIN) {
            Usuario duenio = userRepository.findById(cafeRe.getIdDuenio())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario ingresado como dueño es invalido"));
            if (duenio.getTipoDeUsuario() != TipoDeUsuario.DUENIO) {
                throw new AccessDeniedException("El usuario que esta ingresando no esta verificado como dueño");
            }
            cafe.setDuenio(duenio);
        } else if (actual.getTipoDeUsuario() == TipoDeUsuario.DUENIO) {
            cafe.setDuenio(actual);
        } else {
            throw new AccessDeniedException("No tenes permiso para crear un cafe");
        }
    repo.save(cafe);
    return toDetailDTO(cafe);
    }

    @Transactional
    public CafeDetailDTO modificarCafe(Long id, CafeRequestDTO datosNuevos){
        Cafe existente = buscarPorIdAux(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario actual = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (actual.getTipoDeUsuario() != TipoDeUsuario.ADMIN &&
                !existente.getDuenio().getId().equals(actual.getId())) {
            throw new AccessDeniedException("No tenes permiso para modificar este cafe");
        }
        if (actual.getTipoDeUsuario() == TipoDeUsuario.ADMIN && datosNuevos.getIdDuenio() != null) {
            Usuario nuevoDuenio = userRepository.findById(datosNuevos.getIdDuenio())
                    .orElseThrow(() -> new DuenioNoExistenteExeption("El nuevo dueño no es valido"));
            if (nuevoDuenio.getTipoDeUsuario() != TipoDeUsuario.DUENIO) {
                throw new IllegalArgumentException("El usuario que quiere ingresar como dueño no es de ese tipo");
            }
            existente.setDuenio(nuevoDuenio);
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

    //La excepcion esta en la rama de ceci
    public CafeDetailDTO buscarPorId(Long id){
        Cafe cafe = buscarPorIdAux(id);

        return toDetailDTO(cafe);
    }

    @Transactional
    public void eliminarCafe(Long id) {
        Cafe cafe = buscarPorIdAux(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario actual = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (actual.getTipoDeUsuario() != TipoDeUsuario.ADMIN &&
                !cafe.getDuenio().getId().equals(actual.getId())) {
            throw new AccessDeniedException("No tenes permiso para eliminar este cafe");
        }
        repo.deleteById(id);
    }

    //------------------------FILTROS Y DEMAS--------------------------------------------------------------------------------------

    //para admin
    public List<CafeListDTO> filtrarPorDuenio(Long idDuenio){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario actual = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (actual.getTipoDeUsuario() == TipoDeUsuario.DUENIO && !actual.getId().equals(idDuenio)) {
            throw new AccessDeniedException("No tenes permiso para ver los cafes de otro usuario");
        }

        if (actual.getTipoDeUsuario() == TipoDeUsuario.ADMIN) {
            Usuario duenio = userRepository.findById(idDuenio)
                    .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con id: " + idDuenio));

            if (duenio.getTipoDeUsuario() != TipoDeUsuario.DUENIO) {
                throw new IllegalArgumentException("El usuario con id " + idDuenio + " no esta registrado como dueño.");
            }
        }

        List<Cafe> cafes = repo.findByDuenio_Id(idDuenio);

        if (cafes.isEmpty()) {
            System.out.println("No se encontraron cafes del usuario con email: " +actual.getEmail());
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
                cafe.getDuenio().getNombre(),
                cafe.getDuenio().getApellido(),
                cafe.getDuenio().getId()

        );
    }

    private CafeListDTO toListDTO(Cafe cafe) {
        return new CafeListDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio());
    }




}
