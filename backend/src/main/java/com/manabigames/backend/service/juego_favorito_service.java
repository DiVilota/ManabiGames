package com.manabigames.backend.service;

import com.manabigames.backend.dto.JuegoFavoritoDTO;
import com.manabigames.backend.entity.JuegoFavorito;
import com.manabigames.backend.exception.DuplicateGameException;
import com.manabigames.backend.exception.GameNotFoundException;
import com.manabigames.backend.repository.JuegoFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que contiene la lógica de negocio para gestionar juegos favoritos.
 * Maneja conversiones entre Entity y DTO, validaciones de negocio y operaciones CRUD.
 */
@Service
@Transactional
public class JuegoFavoritoService {

    @Autowired
    private JuegoFavoritoRepository repository;

    /**
     * Obtiene todos los juegos favoritos ordenados por fecha de agregado (más recientes primero).
     * 
     * @return Lista de DTOs de juegos favoritos
     */
    @Transactional(readOnly = true)
    public List<JuegoFavoritoDTO> obtenerTodos() {
        return repository.findAllByOrderByFechaAgregadoDesc()
                .stream()
                .map(JuegoFavoritoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los juegos favoritos ordenados alfabéticamente por nombre.
     * 
     * @return Lista de DTOs de juegos favoritos ordenados por nombre
     */
    @Transactional(readOnly = true)
    public List<JuegoFavoritoDTO> obtenerTodosOrdenadosPorNombre() {
        return repository.findAllByOrderByNombreAsc()
                .stream()
                .map(JuegoFavoritoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un juego favorito por su ID.
     * 
     * @param id ID del juego favorito
     * @return DTO del juego favorito
     * @throws GameNotFoundException si no se encuentra el juego
     */
    @Transactional(readOnly = true)
    public JuegoFavoritoDTO obtenerPorId(Long id) {
        JuegoFavorito juego = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("No se encontró el juego favorito con ID: " + id));
        return new JuegoFavoritoDTO(juego);
    }

    /**
     * Agrega un nuevo juego a favoritos.
     * Valida que no exista duplicado por id_rawg si se proporciona.
     * 
     * @param dto DTO con los datos del juego a agregar
     * @return DTO del juego agregado con su ID generado
     * @throws DuplicateGameException si ya existe un juego con el mismo id_rawg
     */
    public JuegoFavoritoDTO agregar(JuegoFavoritoDTO dto) {
        // Validar duplicado por id_rawg si se proporciona
        if (dto.getIdRawg() != null && repository.existsByIdRawg(dto.getIdRawg())) {
            throw new DuplicateGameException("Ya existe un juego favorito con ID RAWG: " + dto.getIdRawg());
        }

        // Convertir DTO a Entity
        JuegoFavorito juego = new JuegoFavorito();
        juego.setNombre(dto.getNombre());
        juego.setImagen(dto.getImagen());
        juego.setConsola(dto.getConsola());
        juego.setAño(dto.getAño());
        juego.setIdRawg(dto.getIdRawg());
        juego.setDescripcion(dto.getDescripcion());
        juego.setFechaAgregado(LocalDateTime.now());

        // Guardar en base de datos
        JuegoFavorito juegoGuardado = repository.save(juego);

        // Retornar DTO con ID generado
        return new JuegoFavoritoDTO(juegoGuardado);
    }

    /**
     * Actualiza un juego favorito existente.
     * 
     * @param id ID del juego a actualizar
     * @param dto DTO con los nuevos datos
     * @return DTO del juego actualizado
     * @throws GameNotFoundException si no se encuentra el juego
     * @throws DuplicateGameException si el nuevo id_rawg ya existe en otro juego
     */
    public JuegoFavoritoDTO actualizar(Long id, JuegoFavoritoDTO dto) {
        JuegoFavorito juegoExistente = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("No se encontró el juego favorito con ID: " + id));

        // Validar duplicado por id_rawg si cambió y se proporciona
        if (dto.getIdRawg() != null && 
            !dto.getIdRawg().equals(juegoExistente.getIdRawg()) &&
            repository.existsByIdRawg(dto.getIdRawg())) {
            throw new DuplicateGameException("Ya existe otro juego favorito con ID RAWG: " + dto.getIdRawg());
        }

        // Actualizar campos
        juegoExistente.setNombre(dto.getNombre());
        juegoExistente.setImagen(dto.getImagen());
        juegoExistente.setConsola(dto.getConsola());
        juegoExistente.setAño(dto.getAño());
        juegoExistente.setIdRawg(dto.getIdRawg());
        juegoExistente.setDescripcion(dto.getDescripcion());
        // Mantener la fecha de agregado original

        JuegoFavorito juegoActualizado = repository.save(juegoExistente);
        return new JuegoFavoritoDTO(juegoActualizado);
    }

    /**
     * Elimina un juego favorito por su ID.
     * 
     * @param id ID del juego a eliminar
     * @throws GameNotFoundException si no se encuentra el juego
     */
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new GameNotFoundException("No se encontró el juego favorito con ID: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Busca juegos favoritos por nombre (búsqueda parcial e insensible a mayúsculas).
     * 
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de DTOs que coinciden con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<JuegoFavoritoDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(JuegoFavoritoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca juegos favoritos por consola.
     * 
     * @param consola Nombre de la consola
     * @return Lista de DTOs de juegos de esa consola
     */
    @Transactional(readOnly = true)
    public List<JuegoFavoritoDTO> buscarPorConsola(String consola) {
        return repository.findByConsolaIgnoreCase(consola)
                .stream()
                .map(JuegoFavoritoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca juegos favoritos por año.
     * 
     * @param año Año de lanzamiento
     * @return Lista de DTOs de juegos de ese año
     */
    @Transactional(readOnly = true)
    public List<JuegoFavoritoDTO> buscarPorAño(Integer año) {
        return repository.findByAño(año)
                .stream()
                .map(JuegoFavoritoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si existe un juego con un ID de RAWG específico.
     * 
     * @param idRawg ID del juego en RAWG
     * @return true si existe, false si no
     */
    @Transactional(readOnly = true)
    public boolean existePorIdRawg(Integer idRawg) {
        return repository.existsByIdRawg(idRawg);
    }
}