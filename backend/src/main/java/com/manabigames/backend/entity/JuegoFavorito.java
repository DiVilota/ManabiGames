package com.manabigames.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un juego favorito en la base de datos.
 * Tabla: juegos_favoritos
 */
@Entity
@Table(name = "juegos_favoritos")
public class JuegoFavorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del juego es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", 
             message = "La imagen debe ser una URL válida que termine en jpg, jpeg, png, gif o webp",
             flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "imagen", length = 500)
    private String imagen;

    @Size(max = 100, message = "La consola no puede exceder 100 caracteres")
    @Column(name = "consola", length = 100)
    private String consola;

    @Min(value = 1970, message = "El año debe ser mayor o igual a 1970")
    @Max(value = 2024, message = "El año no puede ser mayor a 2024")
    @Column(name = "año")
    private Integer año;

    @Column(name = "id_rawg", unique = true)
    private Integer idRawg;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_agregado", nullable = false)
    private LocalDateTime fechaAgregado;

    // Constructor vacío (requerido por JPA)
    public JuegoFavorito() {
    }

    // Constructor con parámetros básicos
    public JuegoFavorito(String nombre, String imagen, String consola, 
                        Integer año, Integer idRawg, String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.consola = consola;
        this.año = año;
        this.idRawg = idRawg;
        this.descripcion = descripcion;
        this.fechaAgregado = LocalDateTime.now();
    }

    // Método automático para establecer fecha antes de persistir
    @PrePersist
    protected void onCreate() {
        if (fechaAgregado == null) {
            fechaAgregado = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Integer getIdRawg() {
        return idRawg;
    }

    public void setIdRawg(Integer idRawg) {
        this.idRawg = idRawg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    @Override
    public String toString() {
        return "JuegoFavorito{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", consola='" + consola + '\'' +
                ", año=" + año +
                ", idRawg=" + idRawg +
                ", fechaAgregado=" + fechaAgregado +
                '}';
    }
}