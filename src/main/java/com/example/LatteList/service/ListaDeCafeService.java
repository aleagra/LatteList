package com.example.LatteList.service;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListRequestDTO;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.ListaDeCafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListaDeCafeService {
    @Autowired
    ListaDeCafeRepository listaDeCafeRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CafeRepository cafeRepository;

    public List<ListaDeCafe> visualizarListas(){
        Usuario usuario = userService.getUsuarioAutenticado();
        return usuario.getListasDeCafes();
    }

    public ListaDeCafe buscarLista(Long id) {
        Usuario usuario = userService.getUsuarioAutenticado();
        return usuario.getListasDeCafes().stream()
                .filter(lista -> lista.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No se encuentra una lista con ID " + id));
    }

    public ResponseEntity<Map<String, String>> agregarListaDeCafe(UsuarioListRequestDTO dto) {
        Usuario usuario = userService.getUsuarioAutenticado();

        boolean yaExiste = usuario.getListasDeCafes().stream()
                .anyMatch(lista -> lista.getNombre().equalsIgnoreCase(dto.getNombre()));

        if (yaExiste) {
            throw new IllegalArgumentException("Ya tenés una lista con ese nombre.");
        }

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
        Usuario usuario = userService.getUsuarioAutenticado();
        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }
        lista.setNombre(dto.getNombre());
        listaDeCafeRepository.save(lista);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El nombre de la lista fue modificado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarLista(Long listaId) {
        Usuario usuario = userService.getUsuarioAutenticado();

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

    public ResponseEntity<Map<String, String>> agregarCafeALaLista(Long listaId, Long cafeId) {
        Usuario usuario = userService.getUsuarioAutenticado();
        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }

        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("Café no encontrado"));

        lista.cargarUnCafe(cafe);
        listaDeCafeRepository.save(lista);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El cafe fue agregado correctamente.");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarCafeDeLista(Long listaId, Long cafeId) {
        Usuario usuario = userService.getUsuarioAutenticado();

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
