package com.example.LatteList.repository;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    //Busca por coincidencia parcial ignarando mayus y minus
  List<Cafe> findByNombreContainingIgnoreCase(String nombre);

  List<Cafe> findByEtiquetasContaining(Etiquetas etiqueta);

  //busqa por direccion de forma parcial
  List<Cafe> findByDireccionContainingIgnoreCase(String direccion);

  List<Cafe> findByCostoPromedio(CostoPromedio costo);

  @Query(value = "SELECT * FROM cafe ORDER BY RAND() LIMIT 1", nativeQuery = true)
  Optional<Cafe> obtenerCafeAleatorio();

  List<Cafe> findByDuenio_Id(Long id);
}
