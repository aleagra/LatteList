package com.example.LatteList.DTOs.ResenaDTOs;


import java.time.LocalDate;

public class ResenaListDTO {

        private Long id; // ede resenaaaaaaaaaa
        private Integer puntuacionGeneral;
        private Integer puntuacionPrecio;
        private Integer puntuacionAtencion;
        private String comentario;
        private LocalDate fecha;
        private String nombreUsuario;

        public ResenaListDTO() {}

    public ResenaListDTO(Long id, Integer puntuacionGeneral, Integer puntuacionPrecio, Integer puntuacionAtencion, String comentario, LocalDate fecha, String nombreUsuario) {
        this.id = id;
        this.puntuacionGeneral = puntuacionGeneral;
        this.puntuacionPrecio = puntuacionPrecio;
        this.puntuacionAtencion = puntuacionAtencion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.nombreUsuario = nombreUsuario;
    }

// Getters y Setters


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

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getPuntuacionGeneral() {
            return puntuacionGeneral;
        }

        public void setPuntuacionGeneral(Integer puntuacionGeneral) {
            this.puntuacionGeneral = puntuacionGeneral;
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

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }
    }

