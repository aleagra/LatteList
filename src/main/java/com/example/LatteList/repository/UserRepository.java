package com.example.LatteList.repository;

import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByTipoDeUsuario(TipoDeUsuario tipoDeUsuario);;
    List<Usuario> findByNombre(String nombre);
    List<Usuario> findByApellido(String apellido);
    List<Usuario> findByNombreAndApellido(String nombre, String apellido);
}
