package com.example.LatteList.model;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //uso integer y no int, para q me acepte null.
    @Column(nullable = false)
    private Integer puntuacionPrecio;

    @Column(nullable = false)
    private Integer puntuacionAtencion;

    @Column(nullable = false)
    private String comentario;


    private LocalDate fecha = LocalDate.now();

    @Column(nullable = false)
    private Integer puntuacionGeneral;

    //el id de usuario. seria la foreign key
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //foreing de cafe.
    @ManyToOne(optional = false)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    // Constructores
    public Resena() {}

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Resena{" +
                "id=" + id +
                ", puntuacionPrecio=" + puntuacionPrecio +
                ", puntuacionAtencion=" + puntuacionAtencion +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                ", puntuacionGeneral=" + puntuacionGeneral +
                ", usuario=" + usuario.getId() +
                ", cafe=" + cafe.getId() +
                '}';
    }
}
