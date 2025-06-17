package com.example.LatteList.controller;
import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.*;
import com.example.LatteList.Enums.TipoDeUsuario;
import com.example.LatteList.model.ListaDeCafe;
import com.example.LatteList.service.ListaDeCafeService;
import com.example.LatteList.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ListaDeCafeService listaDeCafeService;

    public UserController(UserService userService, ListaDeCafeService listaDeCafeService) {
        this.userService = userService;
        this.listaDeCafeService = listaDeCafeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioListDTO> listar() {
        return userService.listarUsuarios();
    }
    
    @GetMapping("/{id}")
    public UsuarioDetailDTO buscarPorId(@PathVariable Long id) {
        return userService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioDetailDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO req
    ) {
        UsuarioDetailDTO creado = userService.crearUsuario(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public UsuarioDetailDTO modificar(
            @Valid @RequestBody UsuarioRequestDTO req
    ) {
        return userService.modificarMiUsuario(req);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        userService.eliminarUsuario(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rol/{tipo}")
    public ResponseEntity<List<UsuarioListDTO>> findByTipoDeUsuario(
            @PathVariable TipoDeUsuario tipo
    ) {
        List<UsuarioListDTO> usuarios = userService.findByTipoUsuario(tipo);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<UsuarioListDTO>> findByNombre(@PathVariable String nombre) {
        List<UsuarioListDTO> usuarios = userService.findByNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<UsuarioListDTO>> findByApellido(@PathVariable String apellido) {
        List<UsuarioListDTO> usuarios = userService.findByApellido(apellido);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCuenta() {
        userService.eliminarCuenta();
    }

    @GetMapping("/list")
    public List<ListaDeCafe> visualizarUsuarios(){
        return listaDeCafeService.visualizarListas();
    }

    @PostMapping("/list/nueva")
    public void agregarListaDeCafe(@RequestBody UsuarioListRequestDTO dto){
        listaDeCafeService.agregarListaDeCafe(dto);
    }

    @PutMapping("/list/{id}")
    public void modificarNombreLista(@PathVariable Long id, @RequestBody UsuarioListRequestDTO nombre){
        listaDeCafeService.modificarNombreLista(id, nombre);
    }

    @DeleteMapping("/list/{id}")
    public void eliminarLista(@PathVariable Long id){
        listaDeCafeService.eliminarLista(id);
    }

    @PostMapping("/list/{id}/cafe")
    public void agregarCafeAlaLista(@PathVariable Long id, @RequestBody CafeRequestDTO cafe){
        listaDeCafeService.agregarCafeALaLista(id, cafe);
    }

    @DeleteMapping("/list/{listaId}/cafe/{cafeId}")
    public void eliminarCafeDeLista(@PathVariable Long listaId, @PathVariable Long cafeId) {
        listaDeCafeService.eliminarCafeDeLista(listaId, cafeId);
    }
}