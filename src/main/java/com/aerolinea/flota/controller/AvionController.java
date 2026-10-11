package com.aerolinea.flota.controller;

import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import com.aerolinea.flota.service.AvionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aviones")
@Tag(name = "Aviones", description = "Gestión operacional de la flota de aviones")
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los aviones")
    public ResponseEntity<List<Avion>> listarTodos() {
        return ResponseEntity.ok(avionService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avión por ID")
    public ResponseEntity<Avion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avionService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear avión (enviar aerolinea con su id)")
    public ResponseEntity<Avion> crear(@Valid @RequestBody Avion avion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avionService.crear(avion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar avión completo")
    public ResponseEntity<Avion> actualizar(@PathVariable Long id, @Valid @RequestBody Avion avion) {
        return ResponseEntity.ok(avionService.actualizar(id, avion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar avión")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        avionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ubicacion/{ubicacion}")
    @Operation(summary = "Filtrar aviones por ubicación")
    public ResponseEntity<List<Avion>> listarPorUbicacion(@PathVariable UbicacionAvion ubicacion) {
        return ResponseEntity.ok(avionService.listarPorUbicacion(ubicacion));
    }

    @GetMapping("/mantenimiento")
    @Operation(summary = "Listar aviones en mantenimiento")
    public ResponseEntity<List<Avion>> listarEnMantenimiento() {
        return ResponseEntity.ok(avionService.listarEnMantenimiento());
    }
}
