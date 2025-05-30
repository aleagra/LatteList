package com.example.LatteList.DTOs.ResenaDTOs;

public class ResenaRequestDTO {

  @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

  @NotNull(message = "El ID del café es obligatorio")
    private Long cafeId;

  @Min(value = 1, message = "La puntuación debe ser al menos 1")
  @Max(value = 5, message = "La puntuación no puede superar 5")
    private Integer puntuacionPrecio;

  @Min(value = 1, message = "La puntuación debe ser al menos 1")
  @Max(value = 5, message = "La puntuación no puede superar 5")
    private Integer puntuacionAtencion;

  @Size(max = 255, message = "El comentario no puede superar los 255 caracteres")
  @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

  @Min(value = 1, message = "La puntuación general debe ser al menos 1")
  @Max(value = 5, message = "La puntuación general no puede superar 5")
  private Integer puntuacionGeneral;
//No se si las puntuaciones deberian ser obligatorias, que opinan?

    public ResenaRequestDTO() {
    }



    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCafeId() {
        return cafeId;
    }

    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
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

    public Integer getPuntuacionGeneral() {
        return puntuacionGeneral;
    }

    public void setPuntuacionGeneral(Integer puntuacionGeneral) {
        this.puntuacionGeneral = puntuacionGeneral;
    }
}
