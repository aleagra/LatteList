package com.example.LatteList.service;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaDetailDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaListDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
import com.example.LatteList.exception.AccessDeniedException;
import com.example.LatteList.exception.CafeNotFoundException;
import com.example.LatteList.exception.ResenaNotFoundException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Resena;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.ResenaRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResenaService {

    private final CafeRepository cafeRepository;
    private final ResenaRepository resenaRepository;
    private final UserRepository userRepository;

    public ResenaService(CafeRepository cafeRepository, ResenaRepository resenaRepository, UserRepository userRepository) {
        this.cafeRepository = cafeRepository;
        this.resenaRepository = resenaRepository;
        this.userRepository = userRepository;
    }


    public ResenaDetailDTO postReserna(ResenaRequestDTO dto) {
        //1 obtengo el usuario que esta ingresando.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        //2 busco el cafe.
        Cafe cafe = cafeRepository.findById(dto.getCafeId())
                .orElseThrow(() -> new CafeNotFoundException("El café no existe."));
        //3 creo la resenaaaaa
        Resena resena = new Resena();

        resena.setPuntuacionPrecio(dto.getPuntuacionPrecio());
        resena.setPuntuacionAtencion(dto.getPuntuacionAtencion());
        resena.setComentario(dto.getComentario());
        resena.setPuntuacionGeneral(dto.getPuntuacionGeneral());
        resena.setUsuario(usuario);
        resena.setCafe(cafe);
        resenaRepository.save(resena);

        return toDetailDTO(resena);
    }


    public List<ResenaListDTO> getResenasPorCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("No se encontró el café con id: " + cafeId));

        return resenaRepository.findByCafe(cafe)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    public List<ResenaListDTO> getResenasDelCliente() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        return resenaRepository.findByUsuario(usuario)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    public Resena getResenaById(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException("Reseña no encontrada con ID: " + id));
    }

    public void deleteResena(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMIN"));


        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException("No se encontró la reseña con ID: " + id));

        // Si no es admin, validar que la reseña le pertenezca
        if (!esAdmin && !resena.getUsuario().getEmail().equals(email)) {
            throw new AccessDeniedException("No puedes eliminar esta reseña, no es tuya.");
        }

        resenaRepository.deleteById(id);
    }

    //metodos extra
    public ResenaDetailDTO toDetailDTO(Resena resena) {
        ResenaDetailDTO dto = new ResenaDetailDTO();

        dto.setId(resena.getId());
        dto.setPuntuacionPrecio(resena.getPuntuacionPrecio());
        dto.setPuntuacionAtencion(resena.getPuntuacionAtencion());
        dto.setComentario(resena.getComentario());
        dto.setFecha(resena.getFecha());
        dto.setPuntuacionGeneral(resena.getPuntuacionGeneral());

        if (resena.getUsuario() != null) {
            dto.setUsuarioId(resena.getUsuario().getId());
            dto.setUsuarioNombre(resena.getUsuario().getNombre());  // o username si querés
            dto.setEmail(resena.getUsuario().getEmail());
        }

        if (resena.getCafe() != null) {
            dto.setCafeId(resena.getCafe().getId());
            dto.setCafeNombre(resena.getCafe().getNombre());
        }

        return dto;
    }


    private ResenaListDTO toDTO(Resena resena) {
        ResenaListDTO dto = new ResenaListDTO();
        dto.setComentario(resena.getComentario());
        dto.setPuntuacionPrecio(resena.getPuntuacionPrecio());
        dto.setPuntuacionAtencion(resena.getPuntuacionAtencion());
        dto.setPuntuacionGeneral(resena.getPuntuacionGeneral());
        dto.setNombreUsuario(resena.getUsuario().getNombre());
        dto.setFecha(resena.getFecha());
        return dto;
    }


}
