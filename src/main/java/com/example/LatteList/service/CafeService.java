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
                map(c -> new CafeListDTO(c.getId(), c.getNombre(), c.getDireccion(), c.getCostoPromedio())).
                toList();
    }

    //La excepcion esta en la rama de ceci
    public CafeDetailDTO buscarPorId(Long id){
        Cafe cafe = repo.findById(id).
                orElseThrow(() -> new CafeNotFoundException("El cafe con id "+id+" no existe"));
        return new CafeDetailDTO(cafe.getId(), cafe.getNombre(), cafe.getDireccion(), cafe.getCostoPromedio(),
                cafe.getLogo(), cafe.getInstagramURL(), cafe.getEtiquetas(), cafe.getDueño().getNombre());
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
                orElseThrow(() -> DuenioNoExistenteExeption("El usuario ingresado como duenio no es valido"));*/

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
}
