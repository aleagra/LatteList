package com.example.LatteList.service;

import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.ListaDeCafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ListaDeCafeService {
    @Autowired
    ListaDeCafeRepository listaDeCafeRepository;

    @Autowired
    UserRepository userRepository;

    public void agregarListaDeCafe(String nombreLista) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe nuevaLista = new ListaDeCafe();
        nuevaLista.setNombre(nombreLista);
        nuevaLista.setUsuario(usuario);

        usuario.getListasDeCafes().add(nuevaLista);
        userRepository.save(usuario);
    }

    public void modificarNombreLista(Long listaId, String nuevoNombre) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }

        lista.setNombre(nuevoNombre);
        listaDeCafeRepository.save(lista);
    }

    public void eliminarLista(Long listaId) {
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
    }

    public void agregarCafeALaLista(Long listaId, Cafe cafe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ListaDeCafe lista = listaDeCafeRepository.findById(listaId)
                .orElseThrow(() -> new EntityNotFoundException("Lista no encontrada"));

        if (!lista.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta lista.");
        }

        lista.cargarUnCafe(cafe);
        listaDeCafeRepository.save(lista);
    }

    public void eliminarCafeDeLista(Long listaId, Long cafeId) {
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
    }


}
