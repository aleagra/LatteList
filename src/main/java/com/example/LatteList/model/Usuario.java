package com.example.LatteList.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del usuario no puede ser nulo")
    @Size(min = 6, max = 20,message = "El nombre debe contener entre 6 y 20 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido del usuario no puede ser nulo")
    @Size(min = 6, max = 20,message = "El apellido debe contener entre 6 y 20 caracteres")
    @Column(nullable = false)
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true,nullable = false)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20,message = "La contraseña debe contener entre 6 y 20 caracteres")
    private String contrasena;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cafe> listasDeCafes;

    public List<Cafe> getListasDeCafes() {
        return listasDeCafes;
    }

    public void setListasDeCafes(List<Cafe> listasDeCafes) {
        this.listasDeCafes = listasDeCafes;
    }

    public @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 20, message = "La contraseña debe contener entre 6 y 20 caracteres") String getContrasena() {
        return contrasena;
    }

    public void setContrasena(@NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 20, message = "La contraseña debe contener entre 6 y 20 caracteres") String contrasena) {
        this.contrasena = contrasena;
    }

    public @Email(message = "El email debe tener un formato válido") @NotBlank(message = "El email es obligatorio") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "El email debe tener un formato válido") @NotBlank(message = "El email es obligatorio") String email) {
        this.email = email;
    }

    public @NotBlank(message = "El apellido del usuario no puede ser nulo") @Size(min = 6, max = 20, message = "El apellido debe contener entre 6 y 20 caracteres") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank(message = "El apellido del usuario no puede ser nulo") @Size(min = 6, max = 20, message = "El apellido debe contener entre 6 y 20 caracteres") String apellido) {
        this.apellido = apellido;
    }

    public @NotBlank(message = "El nombre del usuario no puede ser nulo") @Size(min = 6, max = 20, message = "El nombre debe contener entre 6 y 20 caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre del usuario no puede ser nulo") @Size(min = 6, max = 20, message = "El nombre debe contener entre 6 y 20 caracteres") String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", listasDeCafes=" + listasDeCafes +
                '}';
    }
}
