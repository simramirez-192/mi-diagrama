package com.aerolinea.flota.service;

import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import java.util.List;

public interface AvionService {

    List<Avion> listarTodos();

    Avion buscarPorId(Long id);

    Avion crear(Avion avion);

    Avion actualizar(Long id, Avion avion);

    void eliminar(Long id);

    List<Avion> listarPorUbicacion(UbicacionAvion ubicacion);

    List<Avion> listarEnMantenimiento();
}
