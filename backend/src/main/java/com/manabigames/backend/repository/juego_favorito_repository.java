package com.manabigames.backend.repository;

import com.manabigames.backend.entity.JuegoFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de JuegoFavorito.
 * Extiende JpaRepository para funcionalidad básica de base de datos.
 */
@Repository
public interface JuegoFavoritoRepository extends JpaRepository<JuegoFavorito, Long> {

    /**
     * Busca un juego favorito por su ID de RAWG.
     * Útil para evitar duplicados desde la API externa.
     * 
     * @param idRawg ID del juego en la API RAWG
     * @return Optional con el juego si existe
     */
    Optional<JuegoFavorito> findByIdRawg(Integer idRawg);

    /**
     * Verifica si existe un juego con el ID de RAWG dado.
     * 
     * @param idRawg ID del juego en la API RAWG
     * @return true si existe, false si no
     */
    boolean existsByIdRawg(Integer idRawg);

    /**
     * Busca juegos favoritos por nombre (búsqueda insensible a mayúsculas/minúsculas).
     * 
     * @param nombre Nombre del juego a buscar
     * @return Lista de juegos que coinciden con el nombre
     */
    @Query("SELECT j FROM JuegoFavorito j WHERE LOWER(j.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<JuegoFavorito> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    /**
     * Busca juegos favoritos por consola.
     * 
     * @param consola Nombre de la consola
     * @return Lista de juegos de esa consola
     */
    List<JuegoFavorito> findByConsolaIgnoreCase(String consola);

    /**
     * Busca juegos favoritos por año específico.
     * 
     * @param año Año de lanzamiento del juego
     * @return Lista de juegos de ese año
     */
    List<JuegoFavorito> findByAño(Integer año);

    /**
     * Busca juegos favoritos por rango de años.
     * 
     * @param añoInicio Año de inicio del rango
     * @param añoFin Año de fin del rango
     * @return Lista de juegos en ese rango de años
     */
    @Query("SELECT j FROM JuegoFavorito j WHERE j.año BETWEEN :añoInicio AND :añoFin ORDER BY j.año")
    List<JuegoFavorito> findByAñoBetween(@Param("añoInicio") Integer añoInicio, 
                                        @Param("añoFin") Integer añoFin);

    /**
     * Obtiene todos los juegos favoritos ordenados por fecha de agregado (más recientes primero).
     * 
     * @return Lista de juegos ordenados por fecha
     */
    List<JuegoFavorito> findAllByOrderByFechaAgregadoDesc();

    /**
     * Obtiene todos los juegos favoritos ordenados por nombre alfabéticamente.
     * 
     * @return Lista de juegos ordenados por nombre
     */
    List<JuegoFavorito> findAllByOrderByNombreAsc();

    /**
     * Cuenta cuántos juegos favoritos hay de una consola específica.
     * 
     * @param consola Nombre de la consola
     * @return Número de juegos de esa consola
     */
    long countByConsolaIgnoreCase(String consola);
}