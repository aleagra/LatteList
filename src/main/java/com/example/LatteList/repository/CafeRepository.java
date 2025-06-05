package com.example.LatteList.repository;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    //Busca por coincidencia parcial ignarando mayus y minus
  List<Cafe> findByNombreContainingIgnoreCase(String nombre);

  List<Cafe> findByEtiquetasContaining(Etiquetas etiqueta);

  //busqa por direccion de forma parcial
  List<Cafe> findByDireccionContainingIgnoreCase(String direccion);

  List<Cafe> findByCostoPromedio(CostoPromedio costo);
}
