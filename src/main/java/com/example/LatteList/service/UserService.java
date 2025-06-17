package com.example.LatteList.service;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioDetailDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioRequestDTO;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
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
        u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setTipoDeUsuario(r.getTipoDeUsuario());

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

    public UsuarioDetailDTO modificarMiUsuario(UsuarioRequestDTO req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario u = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        u.setNombre(req.getNombre());
        u.setApellido(req.getApellido());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        Usuario actualizado = userRepository.save(u);

        return new UsuarioDetailDTO(
                actualizado.getId(), actualizado.getNombre(), actualizado.getApellido(),
                actualizado.getEmail(), actualizado.getTipoDeUsuario()
        );
    }

    public void eliminarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe usuario con ID " + id);
        }
        userRepository.deleteById(id);
    }

    public void eliminarCuenta() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        userRepository.delete(usuario);
    }

    public UsuarioDetailDTO buscarPorId(Long id) {
        Usuario u = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID " + id));
        return new UsuarioDetailDTO(
                u.getId(), u.getNombre(), u.getApellido(),
                u.getEmail(), u.getTipoDeUsuario()
        );
    }

    public List<UsuarioListDTO> findByTipoUsuario(TipoDeUsuario tipoDeUsuario) {
        List<Usuario> usuarios = userRepository.findByTipoDeUsuario(tipoDeUsuario);

        return usuarios.stream()
                .map(u -> new UsuarioListDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTipoDeUsuario()
                ))
                .toList();
    }

    public List<UsuarioListDTO> findByNombre(String nombre) {
        List<Usuario> usuarios = userRepository.findByNombre(nombre);
        return usuarios.stream()
                .map(u -> new UsuarioListDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTipoDeUsuario()
                ))
                .toList();
    }

    public List<UsuarioListDTO> findByApellido(String apellido) {
        List<Usuario> usuarios = userRepository.findByApellido(apellido);

        return usuarios.stream()
                .map(u -> new UsuarioListDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTipoDeUsuario()
                ))
                .toList();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getTipoDeUsuario())
        );
        System.out.println(authorities);
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getPassword(),
                authorities
        );
    }
}

