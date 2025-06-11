package com.example.LatteList.DTOs.UsuarioDTOs;

import com.example.LatteList.Enums.TipoDeUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre del usuario no puede ser nulo")
    @Size(min = 3, max = 20,message = "El nombre debe contener entre 6 y 20 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del usuario no puede ser nulo")
    @Size(min = 3, max = 20,message = "El apellido debe contener entre 6 y 20 caracteres")
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20,message = "La contraseña debe contener entre 6 y 20 caracteres")
    private String contrasena;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private TipoDeUsuario tipoDeUsuario;

    public UsuarioRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoDeUsuario getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(TipoDeUsuario tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }
}
