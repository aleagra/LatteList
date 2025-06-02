package com.example.LatteList.repository;

import com.example.LatteList.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {

}
