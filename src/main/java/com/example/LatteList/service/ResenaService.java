package com.example.LatteList.service;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaDetailDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaListDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaUserDto;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.exception.AccessDeniedException;
import com.example.LatteList.exception.ResenaNotFoundException;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Resena;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.ResenaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CafeService cafeService;


    public ResenaDetailDTO postReserna(ResenaRequestDTO dto) {
        Usuario u = userService.getUsuarioAutenticado();

        Cafe c = cafeService.buscarPorIdAux(dto.getCafeId());
        Resena resena = new Resena();
        resena.setPuntuacionPrecio(dto.getPuntuacionPrecio());
        resena.setPuntuacionAtencion(dto.getPuntuacionAtencion());
        resena.setComentario(dto.getComentario());
        resena.setPuntuacionGeneral(dto.getPuntuacionGeneral());
        resena.setUsuario(u);
        resena.setCafe(c);
        resenaRepository.save(resena);
        return toDetailDTO(resena);
    }

    public List<ResenaListDTO> getResenasPorCafe(Long cafeId) {
        Cafe cafe = cafeService.buscarPorIdAux(cafeId);

        return resenaRepository.findByCafe(cafe)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ResenaUserDto> getResenasDelCliente() {
        Usuario u = userService.getUsuarioAutenticado();
        return resenaRepository.findByUsuario(u)
                .stream()
                .map(this::resenaUser)
                .toList();
    }

    public ResponseEntity<Map<String, String>> deleteResena(Long id) {
        Usuario usuarioAutenticado = userService.getUsuarioAutenticado();
        boolean esAdmin = usuarioAutenticado.getTipoDeUsuario().equals(TipoDeUsuario.ADMIN);
        Resena resena =  resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException("Reseña no encontrada con ID: " + id));
        if (!esAdmin && !resena.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new AccessDeniedException("No puedes eliminar esta reseña, no es tuya.");
        }
        resenaRepository.delete(resena);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "La reseña fue eliminada correctamente");

        return ResponseEntity.ok(respuesta);
    }

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
            dto.setUsuarioNombre(resena.getUsuario().getNombre());
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
        dto.setId(resena.getId());
        dto.setComentario(resena.getComentario());
        dto.setPuntuacionPrecio(resena.getPuntuacionPrecio());
        dto.setPuntuacionAtencion(resena.getPuntuacionAtencion());
        dto.setPuntuacionGeneral(resena.getPuntuacionGeneral());
        dto.setNombreUsuario(resena.getUsuario().getNombre());
        dto.setFecha(resena.getFecha());
        return dto;
    }

    private ResenaUserDto resenaUser(Resena resena) {
        return new ResenaUserDto(
                resena.getId(),
                resena.getPuntuacionGeneral(),
                resena.getPuntuacionPrecio(),
                resena.getPuntuacionAtencion(),
                resena.getComentario(),
                resena.getFecha()
        );
    }


}
