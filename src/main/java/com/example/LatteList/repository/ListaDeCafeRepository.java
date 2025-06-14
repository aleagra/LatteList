package com.example.LatteList.repository;


import com.example.LatteList.model.ListaDeCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaDeCafeRepository extends JpaRepository<ListaDeCafe, Long> {

    List<ListaDeCafe> findByUsuarioId(Long usuarioId);
}
