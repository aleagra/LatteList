package com.example.LatteList.service;

import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioDetailDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioRequestDTO;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UsuarioDetailDTO crearUsuario(UsuarioRequestDTO r) {
        if (userRepository.findByEmail(r.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(r.getNombre());
        u.setApellido(r.getApellido());
        u.setEmail(r.getEmail());
        u.setContrasena(passwordEncoder.encode(r.getContrasena()));

        Usuario guardado = userRepository.save(u);

        return new UsuarioDetailDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getApellido(),
                guardado.getEmail(),
                guardado.getTipoDeUsuario()
        );
    }

    public List<UsuarioListDTO> listarUsuarios() {
        return userRepository.findAll().stream()
                .map(u -> new UsuarioListDTO(u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getTipoDeUsuario()))
                .toList();
    }

    public UsuarioDetailDTO modificarUsuario(Long id, UsuarioRequestDTO req) {
        Usuario u = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visita no encontrada con ID " + id));
        u.setNombre(req.getNombre());
        u.setApellido(req.getApellido());
        u.setEmail(req.getEmail());
        u.setContrasena(req.getContrasena());
        Usuario actualizado = userRepository.save(u);
        return new UsuarioDetailDTO(
                actualizado.getId(), actualizado.getNombre(), actualizado.getApellido(),
                actualizado.getEmail(),actualizado.getTipoDeUsuario()
        );
    }

    public void eliminarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe usuario con ID " + id);
        }
        userRepository.deleteById(id);
    }

    public UsuarioDetailDTO buscarPorId(Long id) {
        Usuario u = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visita no encontrada con ID " + id));
        return new UsuarioDetailDTO(
                u.getId(), u.getNombre(), u.getApellido(),
                u.getEmail(), u.getTipoDeUsuario()
        );
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: "));
    }

}

