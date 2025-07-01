package com.manabigames.backend.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


public class JuegoFavoritoDTO {

    private Long id;

    @NotBlank(message = "El nombre del juego es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", 
             message = "La imagen debe ser una URL válida que termine en jpg, jpeg, png, gif o webp",
             flags = Pattern.Flag.CASE_INSENSITIVE)
    private String imagen;

    @Size(max = 100, message = "La consola no puede exceder 100 caracteres")
    private String consola;

    @Min(value = 1970, message = "El año debe ser mayor o igual a 1970")
    @Max(value = 2024, message = "El año no puede ser mayor a 2024")
    private Integer año;

    private Integer idRawg;

    private String descripcion;

    private LocalDateTime fechaAgregado;

    // Constructor vacío
    public JuegoFavoritoDTO() {
    }

    // Constructor completo
    public JuegoFavoritoDTO(Long id, String nombre, String imagen, String consola, 
                           Integer año, Integer idRawg, String descripcion, 
                           LocalDateTime fechaAgregado) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.consola = consola;
        this.año = año;
        this.idRawg = idRawg;
        this.descripcion = descripcion;
        this.fechaAgregado = fechaAgregado;
    }

    // Constructor para crear DTO desde Entity
    public JuegoFavoritoDTO(com.manabigames.backend.entity.JuegoFavorito entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.imagen = entity.getImagen();
        this.consola = entity.getConsola();
        this.año = entity.getAño();
        this.idRawg = entity.getIdRawg();
        this.descripcion = entity.getDescripcion();
        this.fechaAgregado = entity.getFechaAgregado();
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
        return "JuegoFavoritoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", consola='" + consola + '\'' +
                ", año=" + año +
                ", idRawg=" + idRawg +
                '}';
    }
}