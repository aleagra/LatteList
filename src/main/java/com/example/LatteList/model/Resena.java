package com.example.LatteList.model;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="resenas")
public class Resena {
    //uso integer y no int, para q me acepte null.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //el id de usuario. seria la foreign key
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //foreing de cafe.
    @ManyToOne(optional = false)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private Integer puntuacionPrecio;
    private Integer puntuacionAtencion;
    private String comentario;

    private LocalDate fecha;

    private Integer puntuacionGeneral;

    // Constructores
    public Resena() {}

    public Resena(Usuario usuario, Cafe cafe, Integer puntuacionPrecio, Integer puntuacionAtencion,
                  String comentario, int puntuacionGeneral) {
        this.usuario = usuario;
        this.cafe = cafe;
        this.puntuacionPrecio = puntuacionPrecio;
        this.puntuacionAtencion = puntuacionAtencion;
        this.comentario = comentario;
        this.fecha = LocalDate.now(); // Fecha actual al momento de crear la reseña
        this.puntuacionGeneral = puntuacionGeneral;
    }


    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
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
}
