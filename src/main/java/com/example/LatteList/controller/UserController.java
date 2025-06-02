package com.example.LatteList.controller;

import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioDetailDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioListDTO;
import com.example.LatteList.DTOs.UsuarioDTOs.UsuarioRequestDTO;
import com.example.LatteList.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @PutMapping("/{id}")
    public UsuarioDetailDTO modificar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO req
    ) {
        return userService.modificarUsuario(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        userService.eliminarUsuario(id);
    }
}