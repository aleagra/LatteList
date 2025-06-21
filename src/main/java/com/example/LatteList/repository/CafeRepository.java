package com.example.LatteList.repository;

import com.example.LatteList.Enums.CostoPromedio;
import com.example.LatteList.Enums.Etiquetas;
import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByNombreContainingIgnoreCase(String nombre);
    List<Cafe> findByEtiquetasContaining(Etiquetas etiqueta);
    List<Cafe> findByDireccionContainingIgnoreCase(String direccion);
    List<Cafe> findByCostoPromedio(CostoPromedio costo);

    @Query(value = "SELECT * FROM cafes WHERE dueño_id IS NOT NULL ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Cafe> obtenerCafeAleatorio();

    List<Cafe> findByDuenioIn(List<Usuario> duenios);
}
