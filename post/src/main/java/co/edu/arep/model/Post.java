package co.edu.arep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 140)
    private String contenido;

    private LocalDateTime fechaCreacion;


    private Long usuarioId;

    private Long hilo;

    public Post() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Post(String contenido, Long usuarioId) {
        this.contenido = contenido;
        this.usuarioId = usuarioId;
    }

    public Post(String contenido, Long usuario, Long hilo) {

        this.contenido = contenido;
        this.usuarioId = usuario;
        this.hilo = hilo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getHilo() {
        return hilo;
    }

    public void setHilo(Long hilo) {
        this.hilo = hilo;
    }
}