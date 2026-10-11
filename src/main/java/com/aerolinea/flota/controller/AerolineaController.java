package com.aerolinea.flota.controller;

import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.service.AerolineaService;
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
@RequestMapping("/api/aerolineas")
@Tag(name = "Aerolíneas", description = "Gestión de aerolíneas (relación 1:N con aviones)")
public class AerolineaController {

    private final AerolineaService aerolineaService;

    public AerolineaController(AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las aerolíneas")
    public ResponseEntity<List<Aerolinea>> listarTodas() {
        return ResponseEntity.ok(aerolineaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aerolínea por ID")
    public ResponseEntity<Aerolinea> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aerolineaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear aerolínea")
    public ResponseEntity<Aerolinea> crear(@Valid @RequestBody Aerolinea aerolinea) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aerolineaService.crear(aerolinea));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar aerolínea")
    public ResponseEntity<Aerolinea> actualizar(@PathVariable Long id, @Valid @RequestBody Aerolinea aerolinea) {
        return ResponseEntity.ok(aerolineaService.actualizar(id, aerolinea));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar aerolínea (solo si no tiene aviones)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        aerolineaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/aviones")
    @Operation(summary = "Listar los aviones de una aerolínea")
    public ResponseEntity<List<Avion>> listarAviones(@PathVariable Long id) {
        return ResponseEntity.ok(aerolineaService.listarAviones(id));
    }
}
