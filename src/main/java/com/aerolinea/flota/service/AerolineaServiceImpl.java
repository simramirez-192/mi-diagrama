package com.aerolinea.flota.service;

import com.aerolinea.flota.exception.DuplicateResourceException;
import com.aerolinea.flota.exception.ResourceNotFoundException;
import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.repository.AerolineaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AerolineaServiceImpl implements AerolineaService {

    private final AerolineaRepository aerolineaRepository;

    public AerolineaServiceImpl(AerolineaRepository aerolineaRepository) {
        this.aerolineaRepository = aerolineaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aerolinea> listarTodas() {
        return aerolineaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Aerolinea buscarPorId(Long id) {
        return aerolineaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una aerolínea con id " + id));
    }

    @Override
    @Transactional
    public Aerolinea crear(Aerolinea aerolinea) {
        if (aerolineaRepository.existsByNombre(aerolinea.getNombre())) {
            throw new DuplicateResourceException("Ya existe la aerolínea " + aerolinea.getNombre());
        }
        if (aerolineaRepository.existsByCodigoIata(aerolinea.getCodigoIata())) {
            throw new DuplicateResourceException("Ya existe el código IATA " + aerolinea.getCodigoIata());
        }
        aerolinea.setId(null);
        return aerolineaRepository.save(aerolinea);
    }

    @Override
    @Transactional
    public Aerolinea actualizar(Long id, Aerolinea datos) {
        Aerolinea existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setCodigoIata(datos.getCodigoIata());
        return aerolineaRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Aerolinea existente = buscarPorId(id);
        if (!existente.getAviones().isEmpty()) {
            throw new DuplicateResourceException(
                    "No se puede eliminar: la aerolínea tiene aviones asociados");
        }
        aerolineaRepository.delete(existente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avion> listarAviones(Long id) {
        Aerolinea aerolinea = buscarPorId(id);
        return new ArrayList<>(aerolinea.getAviones());
    }
}
