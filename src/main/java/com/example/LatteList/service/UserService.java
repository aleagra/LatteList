package com.example.LatteList.service;
import com.example.LatteList.DTOs.CafeDTOs.CafeSinResenasDTO;
import com.example.LatteList.DTOs.ResenaDTOs.ResenaUserDto;
import com.example.LatteList.DTOs.UsuarioDTOs.*;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.model.Usuario;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final CafeRepository cafeRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CafeRepository cafeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cafeRepository = cafeRepository;
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

        ListaDeCafe favoritos = new ListaDeCafe();
        favoritos.setNombre("Favoritos");
        favoritos.setUsuario(u);
        u.getListasDeCafes().add(favoritos);

        Usuario guardado = userRepository.save(u);

        return new UsuarioDetailDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getApellido(),
                guardado.getEmail(),
                guardado.getTipoDeUsuario()
        );
    }

    public Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public List<UsuarioListDTO> listarUsuarios() {
        return userRepository.findAll().stream()
                .map(u -> new UsuarioListDTO(u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getTipoDeUsuario()))
                .toList();
    }

    public ResponseEntity<Map<String, String>> modificarMiUsuario(UsuarioRequestDTO req) {
        Usuario u = getUsuarioAutenticado();

        u.setNombre(req.getNombre());
        u.setApellido(req.getApellido());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setTipoDeUsuario(req.getTipoDeUsuario());

        userRepository.save(u);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario modificado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarUsuario(Long id) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe usuario con ID " + id));

        if (cafeRepository.existsByDuenio(usuario)) {
            throw new IllegalStateException("No se puede eliminar el usuario porque es dueño de uno o más cafés.");
        }

        userRepository.delete(usuario);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario con ID " + id + " eliminado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<Map<String, String>> eliminarCuenta() {
        Usuario u = getUsuarioAutenticado();

        if (cafeRepository.existsByDuenio(u)) {
            throw new IllegalStateException("No podés eliminar tu cuenta porque sos dueño de uno o más cafés.");
        }

        userRepository.delete(u);
        SecurityContextHolder.clearContext();

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Tu cuenta fue eliminada correctamente");

        return ResponseEntity.ok(respuesta);
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
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getPassword(),
                authorities
        );
    }

    public UsuarioCompletoDto getUsuarioCompleto() {
        Usuario u = getUsuarioAutenticado();

        List<ResenaUserDto> resenas = u.getResenas().stream()
                .map(resena -> new ResenaUserDto(
                        resena.getId(),
                        resena.getCafe().getNombre(),
                        resena.getCafe().getDireccion(),
                        resena.getPuntuacionGeneral(),
                        resena.getPuntuacionPrecio(),
                        resena.getPuntuacionAtencion(),
                        resena.getComentario(),
                        resena.getFecha()
                ))
                .toList();

        List<UsuarioListaCafeDTO> listas = u.getListasDeCafes().stream()
                .map(lista -> new UsuarioListaCafeDTO(
                        lista.getId(),
                        lista.getNombre(),
                        lista.getCafes().stream()
                                .map(cafe -> new CafeSinResenasDTO(
                                        cafe.getId(),
                                        cafe.getNombre(),
                                        cafe.getDireccion(),
                                        cafe.getCostoPromedio(),
                                        cafe.getLogo(),
                                        cafe.getInstagramURL(),
                                        cafe.getEtiquetas()
                                ))
                                .toList()
                ))
                .toList();

        return new UsuarioCompletoDto(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTipoDeUsuario(),
                listas,
                resenas
        );
    }

    }

