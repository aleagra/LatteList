package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListRequestDTO;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.ListaDeCafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListaDeCafeService {
    @Autowired
    ListaDeCafeRepository listaDeCafeRepository;

    @Autowired
    UserRepository userRepository;

    public List<ListaDeCafe> visualizarListas(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return usuario.getListasDeCafes();
    }


    public ResponseEntity<Map<String, String>> agregarListaDeCafe(UsuarioListRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe nuevaLista = new ListaDeCafe();
        nuevaLista.setNombre(dto.getNombre());
        nuevaLista.setUsuario(usuario);

        usuario.getListasDeCafes().add(nuevaLista);
        userRepository.save(usuario);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "La lista fue creada correctamente");

        return ResponseEntity.ok(respuesta);
    }


    public ResponseEntity<Map<String, String>> modificarNombreLista(Long listaId, UsuarioListRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        lista.setNombre(dto.getNombre());
        listaDeCafeRepository.save(lista);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El nombre de la lista fue modificado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarLista(Long listaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para eliminar esta lista.");
        }

        listaDeCafeRepository.delete(lista);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "La lista fue eliminada correctamente");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> agregarCafeALaLista(Long listaId, CafeRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }

        // Mapear DTO a entidad Cafe
        Cafe nuevoCafe = new Cafe();
        nuevoCafe.setNombre(dto.getNombre());
        nuevoCafe.setDireccion(dto.getDireccion());
        nuevoCafe.setCostoPromedio(dto.getCostoPromedio());
        nuevoCafe.setLogo(dto.getLogo());
        nuevoCafe.setInstagramURL(dto.getInstagramURL());
        nuevoCafe.setEtiquetas(dto.getEtiquetas());
        nuevoCafe.setDuenio(usuario);

        lista.cargarUnCafe(nuevoCafe);
        listaDeCafeRepository.save(lista);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El cafe fue agregado correctamente.");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarCafeDeLista(Long listaId, Long cafeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }

        boolean eliminado = lista.getCafes().removeIf(cafe -> cafe.getId().equals(cafeId));

        if (!eliminado) {
            throw new EntityNotFoundException("Café no encontrado en la lista.");
        }

        listaDeCafeRepository.save(lista);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El cafe fue eliminado correctamente.");

        return ResponseEntity.ok(respuesta);
    }


}
