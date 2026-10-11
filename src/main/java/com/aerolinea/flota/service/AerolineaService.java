package com.aerolinea.flota.service;

import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import java.util.List;

public interface AerolineaService {

    List<Aerolinea> listarTodas();

    Aerolinea buscarPorId(Long id);

    Aerolinea crear(Aerolinea aerolinea);

    Aerolinea actualizar(Long id, Aerolinea aerolinea);

    void eliminar(Long id);

    List<Avion> listarAviones(Long id);
}
