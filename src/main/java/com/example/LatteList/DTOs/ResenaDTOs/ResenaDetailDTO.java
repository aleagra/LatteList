package com.example.LatteList.DTOs.ResenaDTOs;

import com.example.LatteList.model.Resena;

import java.time.LocalDate;

public class ResenaDetailDTO {

        private Long id;
        private Integer puntuacionPrecio;
        private Integer puntuacionAtencion;
        private String comentario;
        private LocalDate fecha;
        private Integer puntuacionGeneral;

        // Información USUARIO
        private Long usuarioId;
        private String usuarioNombre;  // o usuarioUsername, depende de tu clase Usuario
        private String email;

        // Información CAFEEEEEEEEEEEE
        private Long cafeId;
        private String cafeNombre;

        public ResenaDetailDTO() {}

    public ResenaDetailDTO(Long id, Integer puntuacionPrecio, Integer puntuacionAtencion, String comentario, LocalDate fecha, Integer puntuacionGeneral, Long usuarioId, String usuarioNombre, String email, Long cafeId, String cafeNombre) {
        this.id = id;
        this.puntuacionPrecio = puntuacionPrecio;
        this.puntuacionAtencion = puntuacionAtencion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.puntuacionGeneral = puntuacionGeneral;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.email = email;
        this.cafeId = cafeId;
        this.cafeNombre = cafeNombre;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacionPrecio() {
        return puntuacionPrecio;
    }

    public void setPuntuacionPrecio(Integer puntuacionPrecio) {
        this.puntuacionPrecio = puntuacionPrecio;
    }

    public Integer getPuntuacionAtencion() {
        return puntuacionAtencion;
    }

    public void setPuntuacionAtencion(Integer puntuacionAtencion) {
        this.puntuacionAtencion = puntuacionAtencion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntuacionGeneral() {
        return puntuacionGeneral;
    }

    public void setPuntuacionGeneral(Integer puntuacionGeneral) {
        this.puntuacionGeneral = puntuacionGeneral;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Long getCafeId() {
        return cafeId;
    }

    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }

    public String getCafeNombre() {
        return cafeNombre;
    }

    public void setCafeNombre(String cafeNombre) {
        this.cafeNombre = cafeNombre;
    }


}

