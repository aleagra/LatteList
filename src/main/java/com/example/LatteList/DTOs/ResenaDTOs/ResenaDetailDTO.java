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

        // Información CAFEEEEEEEEEEEE
        private Long cafeId;
        private String cafeNombre;

        public ResenaDetailDTO() {}

        public ResenaDetailDTO(Long id, Integer puntuacionPrecio, Integer puntuacionAtencion,
                               String comentario, LocalDate fecha, Integer puntuacionGeneral,
                               Long usuarioId, String usuarioNombre,
                               Long cafeId, String cafeNombre) {
            this.id = id;
            this.puntuacionPrecio = puntuacionPrecio;
            this.puntuacionAtencion = puntuacionAtencion;
            this.comentario = comentario;
            this.fecha = fecha;
            this.puntuacionGeneral = puntuacionGeneral;
            this.usuarioId = usuarioId;
            this.usuarioNombre = usuarioNombre;
            this.cafeId = cafeId;
            this.cafeNombre = cafeNombre;
        }

        // Getters y setters omitidos por brevedad

        // Método para mapear de entidad a DTO
        public static ResenaDetailDTO fromEntity(Resena resena) {
            return new ResenaDetailDTO(
                    resena.getId(),
                    resena.getPuntuacionPrecio(),
                    resena.getPuntuacionAtencion(),
                    resena.getComentario(),
                    resena.getFecha(),
                    resena.getPuntuacionGeneral(),
                    resena.getUsuario().getId(),
                    resena.getUsuario().getNombre(),  // o getUsername() según tu entidad Usuario
                    resena.getCafe().getId(),
                    resena.getCafe().getNombre()
            );
        }
    }

