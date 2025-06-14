package com.example.LatteList.service;

import com.example.LatteList.DTOs.CafeDTOs.CafeListDTO;
import com.example.LatteList.DTOs.ListaCafeDTOs.ListaCafeListDTO;
import com.example.LatteList.DTOs.ListaCafeDTOs.ListaCafeRequestDTO;
import com.example.LatteList.DTOs.ListaCafeDTOs.ListaCafeDetailDTO;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.ListaDeCafeRepository;
import com.example.LatteList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaDeCafeService {

    @Autowired
    private ListaDeCafeRepository listaRepo;

    @Autowired
    private CafeRepository cafeRepo;

    @Autowired
    private UserRepository userRepo;


    public ListaCafeDetailDTO crearLista(Long usuarioId, ListaCafeRequestDTO lista){
        Usuario usuario = userRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));

        ListaDeCafe listaCafe = new ListaDeCafe();
        listaCafe.setNombre(lista.nombre());
        listaCafe.setUsuario(usuario);

        List<Cafe> cafes = cafeRepo.findAllById(lista.idCafes());
        listaCafe.setCafes(cafes);

        listaRepo.save(listaCafe);

        return toDetailDTO(listaCafe);
    }

    public ListaCafeDetailDTO buscarPorId(Long id) {
        ListaDeCafe lista = listaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + id));
        return toDetailDTO(lista);
    }


    public List<ListaCafeListDTO> buscarListasPorUsuario(Long usuarioId) {
        Usuario usu = userRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));

        return listaRepo.findByUsuarioId(usu.getId()).stream()
                .map(this::toListDTO)
                .toList();
    }


    public ListaCafeDetailDTO modificarLista(Long id, ListaCafeRequestDTO dto) {
        ListaDeCafe existente = listaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + id));

        existente.setNombre(dto.nombre());
        List<Cafe> cafes = cafeRepo.findAllById(dto.idCafes());
        existente.setCafes(cafes);

        listaRepo.save(existente);

        return toDetailDTO(existente);
    }

    
    public void eliminarLista(Long id) {
        if (!listaRepo.existsById(id)) {
            throw new RuntimeException("Lista no encontrada con id " + id);
        }
        listaRepo.deleteById(id);
    }



   // ---------------- DTO converters ----------------

    private ListaCafeDetailDTO toDetailDTO(ListaDeCafe lista) {
        List<CafeListDTO> cafes = lista.getCafes().stream()
                .map(this::toCafeListDTO)
                .toList();

        return new ListaCafeDetailDTO(lista.getId(), lista.getNombre(), cafes);
    }

    private ListaCafeListDTO toListDTO(ListaDeCafe lista) {
        return new ListaCafeListDTO(lista.getId(), lista.getNombre(), lista.getCafes().size());
    }

    private CafeListDTO toCafeListDTO(Cafe cafe) {
        return new CafeListDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio());
    }


}


