package com.example.LatteList.service;

import com.example.LatteList.DTOs.ResenaDTOs.ResenaRequestDTO;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResenaService {
    @Autowired
    CafeRepository cafeRepository;
    @Autowired
    ResenaRepository resenaRepository;
    @Autowired
    UserRepository userRepository;

    public Resena postReserna(ResenaRequestDTO resenaRequestDTO){
        Usuario usuario=userRepository.findById(resenaRequestDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Un usuario registrado debe hacer la resena."));

        Cafe cafe=cafeRepository.findById(resenaRequestDTO.getCafeId())
                .orElseThrow(() -> new CafeNotFoundException("El cafe que quieres resenar no existe. Vuelve a intentar."));

        Resena resena = new Resena();
        resena.setPuntuacionPrecio(resenaRequestDTO.getPuntuacionPrecio());
        resena.setPuntuacionAtencion(resenaRequestDTO.getPuntuacionAtencion());
        resena.setComentario(resenaRequestDTO.getComentario());
        resena.setPuntuacionGeneral(resenaRequestDTO.getPuntuacionGeneral());
        //LA FECHA SE PONE SOLA !!!!!!!!!!!!!!!!1
        resena.setUsuario(usuario);
        resena.setCafe(cafe);

        return resenaRepository.save(resena);
    }

    /*public List<Resena> getAllResenas() {
        return resenaRepository.findAll();
    }*/

    public Resena getResenaById(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException("Reseña no encontrada con ID: " + id));
    }

    public void deleteResena(Long id) {
        Resena resena = getResenaById(id); // Si no existe, ya lanza excepción
        resenaRepository.delete(resena);
    }

    public List<Resena> getResenasPorCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("No se encontró el café con id: " + cafeId));

        return resenaRepository.findByCafe(cafe);
    }

    public List<Resena> getResenasDelCliente(String email) {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con email: " + email));

        return resenaRepository.findByUsuario(usuario);
    }

}
