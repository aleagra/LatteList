package com.example.LatteList.repository;

import com.example.LatteList.model.Cafe;
import com.example.LatteList.model.Resena;
import com.example.LatteList.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena,Long> {
    List<Resena> findByUsuario(Usuario usuario);
    List<Resena> findByCafe(Cafe cafe);
}
