package com.manabigames.backend.controller;

import com.manabigames.backend.dto.JuegoFavoritoDTO;
import com.manabigames.backend.service.JuegoFavoritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "file://"})
public class JuegoFavoritoController {

    @Autowired
    private JuegoFavoritoService service;

    /**
     * GET /favoritos
     * Obtiene todos los juegos favoritos ordenados por fecha (más recientes primero).
     * 
     * @param ordenar Parámetro opcional: "nombre" para ordenar alfabéticamente
     * @return Lista de juegos favoritos
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodos(
            @RequestParam(value = "ordenar", required = false) String ordenar) {
        
        List<JuegoFavoritoDTO> juegos;
        
        if ("nombre".equalsIgnoreCase(ordenar)) {
            juegos = service.obtenerTodosOrdenadosPorNombre();
        } else {
            juegos = service.obtenerTodos();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegos);
        response.put("total", juegos.size());
        response.put("message", "Juegos favoritos obtenidos exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/{id}
     * Obtiene un juego favorito específico por su ID.
     * 
     * @param id ID del juego favorito
     * @return Juego favorito encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        JuegoFavoritoDTO juego = service.obtenerPorId(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juego);
        response.put("message", "Juego favorito encontrado exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * POST /favoritos
     * Agrega un nuevo juego a favoritos.
     * 
     * @param dto Datos del juego a agregar (validados)
     * @return Juego agregado con su ID generado
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregar(@Valid @RequestBody JuegoFavoritoDTO dto) {
        JuegoFavoritoDTO juegoGuardado = service.agregar(dto);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegoGuardado);
        response.put("message", "Juego agregado a favoritos exitosamente");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * PUT /favoritos/{id}
     * Actualiza un juego favorito existente.
     * 
     * @param id ID del juego a actualizar
     * @param dto Nuevos datos del juego (validados)
     * @return Juego actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody JuegoFavoritoDTO dto) {
        
        JuegoFavoritoDTO juegoActualizado = service.actualizar(id, dto);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegoActualizado);
        response.put("message", "Juego favorito actualizado exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /favoritos/{id}
     * Elimina un juego favorito por su ID.
     * 
     * @param id ID del juego a eliminar
     * @return Confirmación de eliminación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Juego favorito eliminado exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/buscar?nombre={nombre}
     * Busca juegos favoritos por nombre (búsqueda parcial).
     * 
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de juegos que coinciden con la búsqueda
     */
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarPorNombre(
            @RequestParam(value = "nombre", required = true) String nombre) {
        
        List<JuegoFavoritoDTO> juegos = service.buscarPorNombre(nombre);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegos);
        response.put("total", juegos.size());
        response.put("message", "Búsqueda completada exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/consola/{consola}
     * Busca juegos favoritos por consola específica.
     * 
     * @param consola Nombre de la consola
     * @return Lista de juegos de esa consola
     */
    @GetMapping("/consola/{consola}")
    public ResponseEntity<Map<String, Object>> buscarPorConsola(@PathVariable String consola) {
        List<JuegoFavoritoDTO> juegos = service.buscarPorConsola(consola);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegos);
        response.put("total", juegos.size());
        response.put("message", "Juegos de " + consola + " obtenidos exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/año/{año}
     * Busca juegos favoritos por año específico.
     * 
     * @param año Año de lanzamiento
     * @return Lista de juegos de ese año
     */
    @GetMapping("/año/{año}")
    public ResponseEntity<Map<String, Object>> buscarPorAño(@PathVariable Integer año) {
        List<JuegoFavoritoDTO> juegos = service.buscarPorAño(año);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", juegos);
        response.put("total", juegos.size());
        response.put("message", "Juegos del año " + año + " obtenidos exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/verificar-rawg/{idRawg}
     * Verifica si ya existe un juego con un ID de RAWG específico.
     * Útil para evitar duplicados antes de agregar.
     * 
     * @param idRawg ID del juego en RAWG
     * @return Información sobre si el juego ya existe
     */
    @GetMapping("/verificar-rawg/{idRawg}")
    public ResponseEntity<Map<String, Object>> verificarIdRawg(@PathVariable Integer idRawg) {
        boolean existe = service.existePorIdRawg(idRawg);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("existe", existe);
        response.put("message", existe ? 
            "Ya existe un juego favorito con este ID de RAWG" : 
            "No existe un juego favorito con este ID de RAWG");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /favoritos/stats
     * Obtiene estadísticas básicas sobre los juegos favoritos.
     * 
     * @return Estadísticas generales
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        List<JuegoFavoritoDTO> todosLosJuegos = service.obtenerTodos();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalJuegos", todosLosJuegos.size());
        
        // Contar juegos por década
        Map<String, Long> juegosPorDecada = new HashMap<>();
        todosLosJuegos.forEach(juego -> {
            if (juego.getAño() != null) {
                int decada = (juego.getAño() / 10) * 10;
                String decadaStr = decada + "s";
                juegosPorDecada.put(decadaStr, juegosPorDecada.getOrDefault(decadaStr, 0L) + 1);
            }
        });
        stats.put("juegosPorDecada", juegosPorDecada);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", stats);
        response.put("message", "Estadísticas obtenidas exitosamente");
        
        return ResponseEntity.ok(response);
    }
}