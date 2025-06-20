package com.example.LatteList.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name="resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer puntuacionPrecio;

    @Column(nullable = false)
    private Integer puntuacionAtencion;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private LocalDate fecha ;

    @Column(nullable = false)
    private Integer puntuacionGeneral;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cafe_id")
    @JsonBackReference
    private Cafe cafe;

    public Resena() {}

    @PrePersist
    protected void onCreate() {
        fecha = LocalDate.now();
    }

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

    //to string.
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
